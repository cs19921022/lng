package cn.edu.hdu.lab505.innovation.zbox.support;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hhx on 2016/11/23.
 */
@Component
public class ProtocolSupport {
    private static final byte byte7F = 0x7F;
    private static final byte byte5E = 0x5E;
    private static final byte byte5F = 0x5F;
    private static final byte byteDC = (byte) 0xDC;
    private static final byte byteFB = (byte) 0xFB;
    private static final byte byteFC = (byte) 0xFC;

    private byte[] byteArray;//数据帧
    private byte[] title;//指令头
    private byte[] reserve;//保留位
    private byte[] terminal;//终端标志
    private byte[] number;//指令序号
    private byte[] businessNo;//业务号
    private byte[] function;//功能字
    private byte[] length;///指令长度
    private byte[] content;//指令内容
    private byte[] checkSum;//校验和
    private byte[] tail;//指令尾部
    private boolean isCorrect;

    public void reset() {
        byteArray = null;
        title = null;
        reserve = null;
        terminal = null;
        number = null;
        businessNo = null;
        function = null;
        length = null;
        content = null;
        checkSum = null;
        tail = null;
        isCorrect = false;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    /**
     * 校验
     */
    public void check() {

        byte realCheckSum = 0;
        for (int i = 1; i < byteArray.length - 2; i++) {
            realCheckSum ^= byteArray[i];
        }
        if (realCheckSum != byteArray[byteArray.length - 2]) {
            isCorrect = false;
        } else {
            isCorrect = true;
        }
    }

    /**
     * 分解
     */
    public void resolve() {
        title = Arrays.copyOfRange(byteArray, 0, 1);
        reserve = Arrays.copyOfRange(byteArray, 1, 3);
        terminal = Arrays.copyOfRange(byteArray, 3, 8);
        number = Arrays.copyOfRange(byteArray, 8, 10);
        businessNo = Arrays.copyOfRange(byteArray, 10, 11);
        function = Arrays.copyOfRange(byteArray, 11, 12);
        length = Arrays.copyOfRange(byteArray, 12, 14);
        content = Arrays.copyOfRange(byteArray, 14, byteArray.length - 2);
        checkSum = Arrays.copyOfRange(byteArray, byteArray.length - 2, byteArray.length - 1);
        tail = Arrays.copyOfRange(byteArray, byteArray.length - 1, byteArray.length);
    }

    /**
     * 根据协议开头结尾标识截取
     */
    public void cutFrame() {
        List<Byte> byteList = new ArrayList<>(512);
        int startIndex = -1;
        for (int i = 0; i < byteArray.length; i++) {
            if (byteArray[i] == 0x7E && startIndex == -1) {
                startIndex = i;
                byteList.add(byteArray[i]);
            } else if (byteArray[i] == 0x7E && startIndex != -1) {
                byteList.add(byteArray[i]);
                break;
            } else if (startIndex != -1) {
                byteList.add(byteArray[i]);
            }

        }
        byte[] bs = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            bs[i] = byteList.get(i);
        }
        byteArray = bs;
    }

    public void escape7E7F() {
        List<Byte> byteList = new ArrayList<>(512);
        for (int i = 0; i < byteArray.length - 1; i++) {
            if (byteArray[i] == byte7F && byteArray[i + 1] == byte5E) {
                byteList.add((byte) 0x7E);
                i++;
            } else if (byteArray[i] == byte7F && byteArray[i + 1] == byte5F) {
                byteList.add((byte) 0x7F);
                i++;
            } else {
                byteList.add(byteArray[i]);
            }
        }
        byteList.add(byteArray[byteArray.length - 1]);
        byte[] temp = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            temp[i] = byteList.get(i);
        }
        byteArray = temp;
    }

    public void escapeDBDC() {
        List<Byte> byteList = new ArrayList<>(512);
        for (int i = 0; i < byteArray.length - 1; i++) {
            if (byteArray[i] == byteDC && byteArray[i + 1] == byteFB) {
                byteList.add((byte) 0xDB);
                i++;
            } else if (byteArray[i] == byteDC && byteArray[i + 1] == byteFC) {
                byteList.add((byte) 0xDC);
                i++;
            } else {
                byteList.add(byteArray[i]);
            }
        }
        byteList.add(byteArray[byteArray.length - 1]);
        byte[] temp = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            temp[i] = byteList.get(i);
        }
        byteArray = temp;
    }


    public ProtocolSupport(byte[] byteArray) {
        this.byteArray = byteArray;
        cutFrame();
        escape7E7F();

        check();
        escapeDBDC();
        resolve();
    }

    public ProtocolSupport() {
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
        cutFrame();
        escape7E7F();

        check();
        escapeDBDC();
        resolve();
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public byte[] getTitle() {
        return title;
    }

    public byte[] getReserve() {
        return reserve;
    }

    public byte[] getTerminal() {
        return terminal;
    }

    public byte[] getNumber() {
        return number;
    }

    public byte[] getBusinessNo() {
        return businessNo;
    }

    public byte[] getFunction() {
        return function;
    }

    public byte[] getLength() {
        return length;
    }

    public byte[] getContent() {
        return content;
    }

    public byte[] getCheckSum() {
        return checkSum;
    }

    public byte[] getTail() {
        return tail;
    }
}
