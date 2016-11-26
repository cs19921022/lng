package cn.edu.hdu.lab505.innovation.zbox.support;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hhx on 2016/11/23.
 */
@Component
public class DataSupport {
    private byte address;//从机地址
    private byte frameAmount;//数据帧数量
    private byte[] content;//指令的内容
    private List<DataFrame> dataFrameList;

    public void reset() {
        address = 0;
        frameAmount = 0;
        content = null;
        dataFrameList = null;
    }

    public DataSupport(byte[] content) {
        this.content = content;
        resolve();
    }

    public void setContent(byte[] content) {
        this.content = content;
        resolve();
    }

    public byte getAddress() {
        return address;
    }

    public byte getFrameAmount() {
        return frameAmount;
    }

    public byte[] getContent() {
        return content;
    }

    public List<DataFrame> getDataFrameList() {
        return dataFrameList;
    }

    public DataSupport() {
    }

    public class DataFrame {
        private byte title;//数据帧头
        private byte address;//从机地址
        private byte dataId;//数据id
        private byte[] length;//数据长度
        private byte[] data;//数据

        public DataFrame(byte title, byte address, byte dataId, byte[] length, byte[] data) {
            this.title = title;
            this.address = address;
            this.dataId = dataId;
            this.length = length;
            this.data = data;
        }

        public byte getTitle() {
            return title;
        }

        public void setTitle(byte title) {
            this.title = title;
        }

        public byte getAddress() {
            return address;
        }

        public void setAddress(byte address) {
            this.address = address;
        }

        public byte getDataId() {
            return dataId;
        }

        public void setDataId(byte dataId) {
            this.dataId = dataId;
        }

        public byte[] getLength() {
            return length;
        }

        public void setLength(byte[] length) {
            this.length = length;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }
    }

    public void resolve() {
        address = content[0];
        frameAmount = content[1];
        dataFrameList = new ArrayList<>(frameAmount);
        int p = 2;
        for (int i = 0; i < frameAmount; i++) {
            byte title = content[p];
            byte address = content[p + 1];
            byte dataId = content[p + 2];
            //byte[] dataLength = {content[p + 3], content[p + 4]};
            byte dataLengthHigh = content[p + 3];
            byte dataLengthLow = content[p + 4];
            int dataLength = (dataLengthHigh << 2) + dataLengthLow;
            byte[] data = new byte[dataLength];
            for (int j = 0; j < dataLength; j++) {
                data[j] = content[p + 5 + j];
            }
            dataFrameList.add(new DataFrame(title, address, dataId, new byte[]{dataLengthHigh, dataLengthLow}, data));
            p = p + 5 + dataLength;
        }
    }
}
