/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot;

import static java.util.Objects.requireNonNull;

import com.google.errorprone.annotations.Var;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Result with optional warnings.
 *
 * @author Michael Vorburger.ch
 */
public class ResultWithWarnings<ResultT, WarningT> {

    private final ResultT result;
    private final List<WarningT> warnings;

    public ResultWithWarnings(ResultT result, List<WarningT> warnings) {
        this.result = requireNonNull(result, "result");
        this.warnings = requireNonNull(warnings, "warnings");
    }

    @SafeVarargs
    public ResultWithWarnings(ResultT result, WarningT... warnings) {
        this.result = requireNonNull(result, "result");
        this.warnings = Arrays.asList(requireNonNull(warnings, "warnings"));
    }

    public ResultWithWarnings(ResultT result) {
        this.result = requireNonNull(result, "result");
        this.warnings = Collections.emptyList();
    }

    public ResultT getResult() {
        return result;
    }

    public List<WarningT> getWarnings() {
        return warnings;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        @Var int hashCode = 1;
        hashCode = prime * hashCode + this.result.hashCode();
        hashCode = prime * hashCode + this.warnings.hashCode();
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked")
        ResultWithWarnings<ResultT, WarningT> other = (ResultWithWarnings<ResultT, WarningT>) obj;
        if (result == null) {
            if (other.result != null) {
                return false;
            }
        } else if (!result.equals(other.result)) {
            return false;
        }
        if (warnings == null) {
            if (other.warnings != null) {
                return false;
            }
        } else if (!warnings.equals(other.warnings)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResultWithWarnings [result=" + result + ", warnings=" + warnings + "]";
    }

}
