package cn.edu.hdu.lab505.innovation.zbox.support;

import cn.edu.hdu.lab505.innovation.util.ByteUtil;
import cn.edu.hdu.lab505.innovation.zbox.domain.Frame;
import org.apache.log4j.Logger;

public class FrameSupport {
    private static final Logger LOGGER = Logger.getLogger(FrameSupport.class);
    private Frame frameStructure;
    private String dataString;
    private int length;
    private byte[] dataByteArr;
    private boolean isCorrect;

    public void reset() {
        frameStructure = new Frame();
        dataString = null;
        length = 0;
        dataByteArr = null;
        isCorrect = true;
    }

    public FrameSupport() {
        this.frameStructure = new Frame();
        this.isCorrect = true;
    }

    protected void check() {
        this.isCorrect = true;
        String instructionLength = frameStructure.getInstructionLength();
        int DECInsLength = Integer.valueOf(instructionLength, 16);
        // 指令内容长度校验
        int contentLength = this.getFrameStructure().getInstructionContent().length();
        if (DECInsLength != contentLength / 2) {
            this.isCorrect = false;
            return;
        }
        // 校验和校验
        int checksumValue = Integer.valueOf(this.getFrameStructure().getChecksum(), 16);
        String checkString = this.dataString.substring(2, this.length - 4);
        int[] arr = new int[checkString.length() / 2];
        int c = 0;
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.valueOf(checkString.substring(c, c + 2), 16);
            c += 2;
        }
        int realChecksum = 0;
        for (int i = 0; i < arr.length; i++) {
            realChecksum ^= arr[i];
        }
        if (realChecksum != checksumValue) {
            this.isCorrect = false;
        }
    }

    protected void analyze() {
        this.dataString = ByteUtil.byteToString(dataByteArr);
        // 处理转义字符
        String target1 = "7F5E";
        String replace1 = "7E";
        String target2 = "7F5F";
        String replace2 = "7F";
        String target3 = "DCFB";
        String replace3 = "DB";
        String target4 = "DCFC";
        String replace4 = "DC";
        this.dataString = this.dataString.replace(target1, replace1);
        this.dataString = this.dataString.replace(target2, replace2);
        this.dataString = this.dataString.replace(target3, replace3);
        this.dataString = this.dataString.replace(target4, replace4);

        this.length = this.dataString.length();
        if (this.length < 32) {
            this.isCorrect = false;
            return;
        }
        // 指令头
        String instructionBegin = this.dataString.substring(0, 2);
        // 保留位
        String reservedBits = this.dataString.substring(2, 6);
        // 终端标识
        String terminalIidentification = this.dataString.substring(6, 16);
        // 指令序号
        String number = this.dataString.substring(16, 20);
        String businessNo = this.dataString.substring(20, 22);
        // 指令功能字
        String function = this.dataString.substring(22, 24);
        // 指令长度
        String instructionLength = this.dataString.substring(24, 28);
        // 指令内容
        String instructionContent = this.dataString.substring(28, this.length - 4);
        // 校验和
        String checksum = this.dataString.substring(length - 4, length - 2);
        // 指令尾
        String instructionEnd = this.dataString.substring(this.length - 2, this.length);
        this.frameStructure.setBusinessNo(businessNo);
        this.frameStructure.setChecksum(checksum);
        this.frameStructure.setFunction(function);
        this.frameStructure.setInstructionBegin(instructionBegin);
        this.frameStructure.setInstructionContent(instructionContent);
        this.frameStructure.setInstructionEnd(instructionEnd);
        this.frameStructure.setInstructionLength(instructionLength);
        this.frameStructure.setNumber(number);
        this.frameStructure.setReservedBits(reservedBits);
        this.frameStructure.setTerminalIidentification(terminalIidentification);
        this.check();
        if (!isCorrect) {
            LOGGER.info("数据校验发现错误");
        }
    }

    public Frame getFrameStructure() {
        return frameStructure;
    }

    public void setFrameStructure(Frame frameStructure) {
        this.frameStructure = frameStructure;
    }

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }

    public byte[] getDataByteArr() {
        return dataByteArr;
    }

    public void setDataByteArr(byte[] dataByteArr) {
        this.dataByteArr = dataByteArr;
        this.analyze();
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

}
