/*
 * Copyright (C) 2018  Danijel Askov
 *
 * This file is part of MicroJava Compiler.
 *
 * MicroJava Compiler is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MicroJava Compiler is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.askov.mjcompiler.util;

import java.io.File;
import java.net.URL;
import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;

/**
 * @author Danijel Askov
 */
public class Log4JUtils {

  private static final Log4JUtils logs = new Log4JUtils();

  public static Log4JUtils instance() {
    return logs;
  }

  public URL findLoggerConfigFile() {
    return Thread.currentThread().getContextClassLoader().getResource("log4j.xml");
  }

  public void prepareLogFile(Logger root) {
    Appender appenderAll = root.getAppender("file_all"),
        appenderError = root.getAppender("file_err"),
        appenderInfo = root.getAppender("file_info");

    if (!(appenderAll instanceof FileAppender fAppenderAll)
        || !(appenderError instanceof FileAppender fAppenderError)
        || !(appenderInfo instanceof FileAppender fAppenderInfo)) {
      return;
    }

    String fNameAll = fAppenderAll.getFile(),
        fNameError = fAppenderError.getFile(),
        fNameInfo = fAppenderInfo.getFile();

    fNameAll = fNameAll.substring(0, fNameAll.lastIndexOf('.'));
    fNameError = fNameError.substring(0, fNameError.lastIndexOf('.'));
    fNameInfo = fNameInfo.substring(0, fNameInfo.lastIndexOf('.'));

    File fAllRenamed = new File(fNameAll + "_" + System.currentTimeMillis() + ".log");
    File fErrorRenamed = new File(fNameError + "_" + System.currentTimeMillis() + ".log");
    File fInfoRenamed = new File(fNameInfo + "_" + System.currentTimeMillis() + ".log");

    fAppenderAll.setFile(fAllRenamed.getAbsolutePath());
    fAppenderAll.activateOptions();
    fAppenderError.setFile(fErrorRenamed.getAbsolutePath());
    fAppenderError.activateOptions();
    fAppenderInfo.setFile(fInfoRenamed.getAbsolutePath());
    fAppenderInfo.activateOptions();
  }
}
