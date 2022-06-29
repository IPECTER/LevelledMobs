package me.lokka30.levelledmobs.bukkit.logic.function.process.action.impl;

import me.lokka30.levelledmobs.bukkit.LevelledMobs;
import me.lokka30.levelledmobs.bukkit.logic.context.Context;
import me.lokka30.levelledmobs.bukkit.logic.function.process.Process;
import me.lokka30.levelledmobs.bukkit.logic.function.process.action.Action;
import me.lokka30.levelledmobs.bukkit.util.Log;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;

public class RunFunctionAction extends Action {

    /* vars */

    private boolean sentError = false;

    /* constructors */

    public RunFunctionAction(
        @NotNull Process process,
        @NotNull CommentedConfigurationNode node
    ) {
        super(process, node);
    }

    /* methods */

    @Override
    public void run(Context context) {
        final var functionIdToRun = getActionNode().node("id").getString("");

        final var potentiallyCircularFunction = context.getLinkedFunctions().stream()
            .anyMatch(otherFunction -> otherFunction.getIdentifier().equals(functionIdToRun));

        final var useCircularFunctionDependencyDetection = LevelledMobs.getInstance()
            .getConfigHandler().getSettingsCfg()
            .getRoot().node("advanced", "circular-function-dependency-detection")
            .getBoolean(false);

        if(useCircularFunctionDependencyDetection && potentiallyCircularFunction) {
            Log.sev(String.format(
                "Blocked potentially recursive call to run function '%s' in process '%s' (parent " +
                    "function '%s'). This protection can be disabled - be advised that recursive " +
                    "calls can result in memory leaks. LM will call 'exit-all' on the cause. " +
                    "This message will only appear once.",
                functionIdToRun,
                getParentProcess().getIdentifier(),
                getParentProcess().getParentFunction().getIdentifier()
            ), true);
            getParentProcess().getParentFunction().setShouldExitAll(true);
            getParentProcess().getParentFunction().setShouldExit(true);
            getParentProcess().setShouldExit(true);
            return;
        }

        final var functionToRunOpt = LevelledMobs.getInstance()
            .getLogicHandler()
            .getFunctions()
            .stream()
            .filter(otherFunction -> otherFunction.getIdentifier().equals(functionIdToRun))
            .findFirst();

        if(functionToRunOpt.isEmpty()) {
            if(hasSentError())
                return;

            Log.sev(String.format(
                "Unable to run function '%s' from process '%s' in function '%s' as function '%s' " +
                    "does not exist.",
                functionIdToRun,
                getParentProcess().getIdentifier(),
                getParentProcess().getParentFunction().getIdentifier(),
                functionIdToRun
            ), true);

            setHasSentError(true);
        } else {
            if(!potentiallyCircularFunction)
                context.withLinkedFunction(getParentProcess().getParentFunction());

            functionToRunOpt.get().run(context, false);
        }
    }

    /* getters and setters */

    public boolean hasSentError() {
        return sentError;
    }

    public void setHasSentError(final boolean state) {
        this.sentError = state;
    }
}