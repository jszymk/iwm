package app.dicom;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomOutputStream;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferUShort;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.Date;

public class DicomExport {

    public static void writeFile(String fileName, BufferedImage img, String patientName, Date date, String comments) throws IOException {
        DataBufferInt buff = (DataBufferInt) img.getData().getDataBuffer();
        int[] data = buff.getData();
        ByteBuffer byteBuf = ByteBuffer.allocate(data.length);
        for (int aData : data) {
            byteBuf.put((byte)(aData&0xff));
        }

        Attributes attribs = new Attributes();
        attribs.setInt(Tag.SamplesPerPixel, VR.US, 1);
        attribs.setInt(Tag.BitsAllocated, VR.US, 8);
        attribs.setInt(Tag.BitsStored, VR.US, 8);
        attribs.setInt(Tag.HighBit, VR.US, 7);
        attribs.setInt(Tag.PixelRepresentation, VR.US, 0);
        attribs.setString(Tag.PhotometricInterpretation, VR.CS, "MONOCHROME2");
        attribs.setInt(Tag.Rows, VR.US, img.getHeight());
        attribs.setInt(Tag.Columns, VR.US, img.getWidth());
        attribs.setBytes(Tag.PixelData, VR.OW, byteBuf.array());

        attribs.setString(Tag.PatientName, VR.PN, patientName);
        attribs.setDate(Tag.Date, VR.DA, date);
        attribs.setString(Tag.ImageComments, VR.LT, comments);

        DicomOutputStream dcmo = new DicomOutputStream(new File(fileName));
        attribs.writeTo(dcmo);
        dcmo.close();
    }
}
