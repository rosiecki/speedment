/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.codegen.model.method;

import com.speedment.codegen.Adder;
import com.speedment.codegen.model.type.Type_;
import com.speedment.codegen.model.annotation.Annotation_;
import com.speedment.codegen.model.modifier.MethodModifier_;
import com.speedment.codegen.model.parameter.Parameter_;
import com.speedment.codegen.model.statement.Statement_;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 *
 * @author pemi
 * @param <T>
 */
public class MethodAdder<T> extends Method_ implements Adder<T> {

    final T parent;
    final Consumer<MethodAdder<T>> updater;

    public MethodAdder(T parent, Consumer<MethodAdder<T>> updater) {
        super();
        this.parent = Objects.requireNonNull(parent);
        this.updater = Objects.requireNonNull(updater);
    }

    @Override
    public T add() {
        updater.accept(this);
        return parent;
    }

    @Override
    public MethodAdder<T> set(Set<MethodModifier_> newSet) {
        super.set(newSet);
        return this;
    }

    @Override
    public MethodAdder<T> setName(CharSequence name) {
        super.setName(name);
        return this;
    }

    @Override
    public MethodAdder<T> setType(Type_ type_) {
        super.setType(type_);
        return this;
    }

    @Override
    public MethodAdder<T> add(Annotation_ annotation) {
        super.add(annotation);
        return this;
    }

    @Override
    public MethodAdder<T> add(Parameter_ field_) {
        super.add(field_);
        return this;
    }

    @Override
    public MethodAdder<T> add(Statement_ statement) {
        super.add(statement);
        return this;
    }

    @Override
    public MethodAdder<T> add(MethodModifier_ firstClassModifier_m, MethodModifier_... restClassModifiers) {
        super.add(firstClassModifier_m, restClassModifiers);
        return this;
    }

    @Override
    public MethodAdder<T> abstract_() {
        super.abstract_();
        return this;
    }

    @Override
    public MethodAdder<T> final_() {
        super.final_();
        return this;
    }

    @Override
    public MethodAdder<T> native_() {
        super.native_();
        return this;
    }

    @Override
    public MethodAdder<T> private_() {
        super.private_();
        return this;
    }

    @Override
    public MethodAdder<T> protected_() {
        super.protected_();
        return this;
    }

    @Override
    public MethodAdder<T> public_() {
        super.public_();
        return this;
    }

    @Override
    public MethodAdder<T> static_() {
        super.static_();
        return this;
    }

    @Override
    public MethodAdder<T> strictfp_() {
        super.strictfp_();
        return this;
    }

    @Override
    public MethodAdder<T> synchronized_() {
        super.synchronized_();
        return this;
    }

}
