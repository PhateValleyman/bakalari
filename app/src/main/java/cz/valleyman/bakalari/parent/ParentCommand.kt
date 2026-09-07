package cz.valleyman.bakalari.parent

sealed class ParentCommand {
    object Unlock : ParentCommand()
    object Lock : ParentCommand()
}
