package cn.edu.hdu.lab505.innovation.service;

import cn.edu.hdu.lab505.innovation.common.AbstractCurdServiceSupport;
import cn.edu.hdu.lab505.innovation.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.innovation.dao.IAccountDao;
import cn.edu.hdu.lab505.innovation.domain.Account;
import cn.edu.hdu.lab505.innovation.domain.Role;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import java.util.*;

/**
 * Created by hhx on 2016/11/19.
 */
@Service
public class AccountService extends AbstractCurdServiceSupport<Account> implements IAccountService {
    private static final Logger LOGGER = Logger.getLogger(AccountService.class);
    @Autowired
    private IAccountDao accountDao;
    @Autowired
    private Cache tokenCache;
    @Autowired
    private Cache accountCache;

    protected ICurdDaoSupport<Account> getCurdDao() {
        return accountDao;
    }

    @Override
    @Transactional
    public String login(String account, String password) throws AccountNotFoundException, FailedLoginException {
        Account a = accountDao.getByAccount(account);
        if (a == null) throw new AccountNotFoundException(account + " does not exist");
        if (!a.getPassword().equals(password)) {
            throw new FailedLoginException("incorrect password was provided");
        }
        String token = new String(Base64.encodeBase64(UUID.randomUUID().toString().getBytes()));
        if (accountCache.get(account) != null) {
            String oldToken = (String) accountCache.get(account).getObjectValue();
            tokenCache.remove(oldToken);
        }
        accountCache.put(new Element(account, token));
        tokenCache.put(new Element(token, a));
        return token;
    }

    @Override
    public void logout(String token) {
        Element et = tokenCache.get(token);
        if (et != null) {
            accountCache.remove(et.getObjectValue());
            tokenCache.remove(token);
        }
    }

    @Override
    @Transactional
    public void updateIgnorePassword(Account entity) {
        Account origin = get(entity.getId());
        origin.setName(entity.getName());
        origin.setType(entity.getType());
        origin.setAddress(entity.getAddress());
        origin.setCenter(entity.getCenter());
        origin.setContact(entity.getContact());
        origin.setContractUnitPrice(entity.getContractUnitPrice());
        origin.setDeliveryMethod(entity.getDeliveryMethod());
        origin.setEarlyWarningThreshold(entity.getEarlyWarningThreshold());
        origin.setIndustry(entity.getIndustry());
        origin.setInvestmentMethod(entity.getInvestmentMethod());
        origin.setInvestmentScale(entity.getInvestmentScale());
        origin.setProgress(entity.getProgress());
        origin.setLinkman(entity.getLinkman());
        origin.setRegion(entity.getRegion());
        origin.setRemark(entity.getRemark());
        origin.setSalesman(entity.getSalesman());
        origin.setUnit(entity.getUnit());
        origin.setStatus(entity.getStatus());
        origin.setSignedClient(entity.getSignedClient());
        origin.setShortName(entity.getShortName());
        origin.setSettlementMode(entity.getSettlementMode());

        update(origin);
    }

    @Override
    public Account getAccountInfo(String token) throws CredentialExpiredException {
        Element et = tokenCache.get(token);
        LOGGER.debug("================" + token);
        if (et == null) {
            throw new CredentialExpiredException();
        }
        return (Account) et.getObjectValue();
    }

    @Override
    @Transactional
    public int createAccount(Account a) {
        try {
            a.setOperationTime(new Date());
            accountDao.insert(a);
            Set<Role> list = new HashSet<>();
            list.add(new Role(2));
            a.setRoles(list);
            return a.getId();
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateKeyException(a.getUsername() + " duplicate");
        }
    }

    @Override
    @Transactional
    public void changePassword(Account entity) {
        Account origin = get(entity.getId());
        origin.setPassword(entity.getPassword());
        update(origin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> findByName(String name) {
        if (name != null) {
            return accountDao.getByName(name);
        }
        return null;
    }

    public IAccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Cache getTokenCache() {
        return tokenCache;
    }

    public void setTokenCache(Cache tokenCache) {
        this.tokenCache = tokenCache;
    }

    public Cache getAccountCache() {
        return accountCache;
    }

    public void setAccountCache(Cache accountCache) {
        this.accountCache = accountCache;
    }
}
