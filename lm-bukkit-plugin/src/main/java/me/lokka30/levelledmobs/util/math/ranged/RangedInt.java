/*
 * This file is Copyright (c) 2020-2022 lokka30.
 * This file is/was present in the LevelledMobs resource.
 * Repository: <https://github.com/lokka30/LevelledMobs>
 * Use of this source code is governed by the GNU GPL v3.0
 * license that can be found in the LICENSE.md file.
 */

package me.lokka30.levelledmobs.util.math.ranged;

import me.lokka30.microlib.maths.Randoms;

public record RangedInt(int min, int max) implements RangedValue<Integer> {

    public static RangedInt parse(String val) {
        String[] split = val.split("-");

        if (split.length == 1) {
            // not ranged, only a single val is specified
            final int i = Integer.parseInt(split[0]);
            return new RangedInt(i, i);
        } else if (split.length == 2) {
            // ranged, as two vals are specified
            return new RangedInt(
                Integer.parseInt(split[0]),
                Integer.parseInt(split[1])
            );
        } else {
            throw new IllegalArgumentException(
                "Incorrect RangedInt formatting for value '" + val + "'.");
        }
    }

    @Override
    public Integer generateRandom() {
        return Randoms.generateRandomInt(min, max);
    }

}