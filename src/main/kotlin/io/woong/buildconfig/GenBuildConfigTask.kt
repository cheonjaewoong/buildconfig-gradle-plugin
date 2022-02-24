/*
 * Copyright 2022 Jaewoong Cheon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.woong.buildconfig

import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.lang.model.element.Modifier

abstract class GenBuildConfigTask : DefaultTask() {
    @TaskAction
    fun execute() {
        val fieldSpec = FieldSpec
            .builder(TypeName.BOOLEAN, "test")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
            .initializer("true")
            .build()

        val classSpec = TypeSpec
            .classBuilder("BuildConfig")
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addField(fieldSpec)
            .build()

        val fileSpec = JavaFile
            .builder("${project.group}", classSpec)
            .build()

        val outputDir = File("${project.buildDir}/generated/sources/buildConfig/kotlin/main")
        fileSpec.writeTo(outputDir)
    }
}
