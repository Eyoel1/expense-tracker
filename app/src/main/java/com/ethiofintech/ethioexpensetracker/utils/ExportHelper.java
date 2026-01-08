package com.ethiofintech.ethioexpensetracker.utils;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

public class ExportHelper {

    public static void generatePdf(List<Map<String, Object>> transactions) {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        int y = 40;
        canvas.drawText("ET-Track: Expense Report", 80, y, paint);

        y += 30;
        for (Map<String, Object> t : transactions) {
            String row = t.get("description") + " : " + t.get("amount") + " ETB";
            canvas.drawText(row, 20, y, paint);
            y += 20;
        }

        document.finishPage(page);

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "EthioReport.pdf");
        try {
            document.writeTo(new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
    }
}
