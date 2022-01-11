/*
 * This file is Copyright (c) 2020-2022 lokka30.
 * This file is/was present in the LevelledMobs resource.
 * Repository: <https://github.com/lokka30/LevelledMobs>
 * Use of this source code is governed by the GNU GPL v3.0
 * license that can be found in the LICENSE.md file.
 */

package me.lokka30.levelledmobs.file.external.groups;

import me.lokka30.levelledmobs.LevelledMobs;
import me.lokka30.levelledmobs.file.external.YamlExternalVersionedFile;
import me.lokka30.levelledmobs.util.Utils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class GroupsFile implements YamlExternalVersionedFile {

    private YamlConfiguration data;
    private final LevelledMobs main;
    private final File file;
    public GroupsFile(final @NotNull LevelledMobs main) {
        this.main = main;
        this.file = new File(getFullPath(main));
    }

    @Override
    public void load(boolean fromReload) {
        // replace if not exists
        if(!exists(main)) { replace(main); }

        // load the data
        data = YamlConfiguration.loadConfiguration(file);

        // run the migrator
        migrate();
    }

    @Override
    public String getNameWithoutExtension() { return "groups"; }

    @Override
    public String getRelativePath() { return getName(); }

    @Override
    public int getSupportedFileVersion() {
        return 1;
    }

    @Override
    public void migrate() {
        switch(compareFileVersion()) {
            case CURRENT:
                return;
            case FUTURE:
                sendFutureFileVersionWarning(main);
                return;
            case OUTDATED:
                boolean shouldBreak = false;
                for(int i = getInstalledFileVersion(); i < getSupportedFileVersion(); i++) {
                    if(shouldBreak) break;

                    Utils.LOGGER.info("Attempting to migrate file '&b" + getName() + "&7' to version '&b" + i + "&7'...");
                    switch(i) {
                        default:
                            // this is reached if there is no migration logic for a specific version.
                            Utils.LOGGER.warning("Migration logic was not programmed for the file version '&b" + i + "&7' " +
                                    "of the file '&b" + getName() + "&7'! Please inform the LevelledMobs developers.");
                            shouldBreak = true;
                            break;
                    }
                }
                Utils.LOGGER.info("Migration complete for file '&b" + getName() + "&7'.");
                return;
            default:
                throw new IllegalStateException(compareFileVersion().toString());
        }
    }

    @NotNull
    @Override
    public YamlConfiguration getData() {
        return data;
    }
}
