/*
 * Copyright (c) 2020-2021  lokka30. Use of this source code is governed by the GNU AGPL v3.0 license that can be found in the LICENSE.md file.
 */

package me.lokka30.levelledmobs.rules;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public record Group<T>(
        @NotNull String     identifier,
        @NotNull HashSet<T> data
) {}
