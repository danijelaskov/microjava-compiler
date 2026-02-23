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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Danijel Askov
 */
public abstract class MJLogger<T> {

  public enum Type {
    INFO_LOGGER,
    ERROR_LOGGER
  }

  protected final Logger log = LoggerFactory.getLogger(getClass());
  private final Type type;
  protected final String messageHead;

  public MJLogger(Type type, String messageHead) {
    this.type = type;
    this.messageHead = messageHead;
  }

  protected abstract String messageBody(T loggedObject, Object... context);

  public final void log(T loggedObject, Integer line, Integer column, Object... context) {
    var head = String.format("%-14s", this.messageHead);
    var location =
        line != null
            ? String.format(
                " (line %3d%s)", line, column != null ? String.format(", column %3d", column) : "")
            : "";
    var body = this.messageBody(loggedObject, context);
    switch (this.type) {
      case INFO_LOGGER -> log.info("{}{}: {}.", head, location, body);
      case ERROR_LOGGER -> log.error("{}{}: {}.", head, location, body);
    }
  }
}
