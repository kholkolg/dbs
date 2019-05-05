package dbs4.logger;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;


class HTMLFormatter extends Formatter {

    @Override
    public String format(LogRecord rec) {
        StringBuilder sb = new StringBuilder();
        sb.append("<tr>\n");

        //color to warnings
        if (rec.getLevel().intValue() >= Level.WARNING.intValue()) {
            sb.append("\t<td style=\"color:red\">").
                append("<b>").
                append(rec.getLevel()).
                append("</b>");
        } else {
            sb.append("\t<td>").
                append(rec.getLevel());
        }
        sb.append("</td>\n").
            append("\t<td>").
            append(rec.getSourceClassName()).
            append(".").append(rec.getSourceMethodName()).
            append("</td>\n").
            append("\t<td>").
            append(calcDate(rec.getMillis())).
            append("</td>\n").
            append("\t<td>").
            append(formatMessage(rec)).
            append("</td>\n").
            append("</tr>\n");

        return sb.toString();
    }

    private String calcDate(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date(millis));
    }

    // this method is called just after the handler using this
    // formatter is created
    @Override
    public String getHead(Handler h) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n<head>\n<style>\n").
            append("table { width: 100% }\n").
            append("th { font:bold 10pt Tahoma; }\n").
            append("th { font:bold 10pt Tahoma; }\n").
            append("td { font:normal 10pt Tahoma; }\n").
            append("h1 {font:normal 11pt Tahoma;}\n").
            append("</style>\n").
            append("</head>\n").
            append("<body>\n").
            append("<h1>").
            append(new Date()).
            append("</h1>\n").
            append("<table border=\"0\" cellpadding=\"5\" cellspacing=\"3\">\n").
            append("<tr align=\"left\">\n").
            append("\t<th style=\"width:10%\">Loglevel</th>\n").
            append("\t<th style=\"width:10%\">Caller</th>\n").
            append("\t<th style=\"width:15%\">Time</th>\n").
            append("\t<th style=\"width:75%\">Log Message</th>\n").
            append("</tr>\n");
        return sb.toString();
    }
    // this method is called just after the handler using this
    // formatter is closed
    @Override
    public String getTail(Handler h) {
        return "</table>\n</body>\n</html>";
    }
}

