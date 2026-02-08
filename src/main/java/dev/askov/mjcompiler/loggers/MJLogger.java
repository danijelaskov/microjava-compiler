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

package dev.askov.mjcompiler.loggers;

import org.apache.log4j.Logger;

/**
 * @author Danijel Askov
 */
public abstract class MJLogger<T> {

  protected enum MJLoggerKind {
    INFO_LOGGER,
    ERROR_LOGER
  }

  protected final Logger log = Logger.getLogger(getClass());
  private final MJLoggerKind kind;
  protected final String messageHead;

  public MJLogger(MJLoggerKind kind, String messageHead) {
    this.kind = kind;
    this.messageHead = messageHead;
  }

  protected abstract String messageBody(T loggedObject, Object... context);

  public final void log(T loggedObject, Integer line, Integer column, Object... context) {
    String message =
        String.format("%-14s", this.messageHead)
            + (line != null
                ? " (line "
                    + String.format("%3d", line)
                    + (column != null ? ", column " + String.format("%3d", column) : "")
                    + ")"
                : "")
            + ": "
            + this.messageBody(loggedObject, context)
            + ".";
    switch (this.kind) {
      case INFO_LOGGER -> log.info(message);
      case ERROR_LOGER -> log.error(message);
    }
  }
}
