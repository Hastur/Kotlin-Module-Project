import entities.Archive
import java.util.Scanner

class ArchivesActions {
    private val scanner = Scanner(System.`in`)
    private var archivesList = mutableListOf<Archive>()

    fun selectAction() {
        val actionsList = listOf(
            Actions.CREATE_ARCHIVE.action,
            Actions.SELECT_ARCHIVE.action,
            Actions.EXIT.action
        )

        println(Actions.SELECT_ACTION.action)
        actionsList.forEachIndexed { index, action ->
            println("$index — $action")
        }

        while (true) {
            val action = scanner.nextLine().toIntOrNull()
            when (action) {
                0 -> {
                    createArchive()
                    break
                }

                1 -> {
                    selectArchive(archivesList)
                    break
                }

                2 -> {
                    println("Выход из программы")
                    break
                }

                else -> println(Actions.TRY_AGAIN.action)
            }
        }
    }

    private fun createArchive() {
        while (true) {
            println("Введите название архива:")
            val name = scanner.nextLine()
            if (name.trim().isNotEmpty()) {
                archivesList.add(Archive(name, mutableListOf()))
                println("Архив «$name» добавлен")
                moreArchives(listOf(Actions.MORE.action, Actions.BACK.action))
                break
            } else println("Название не может быть пустым")
        }
    }

    fun selectArchive(archivesList: MutableList<Archive>) {
        this.archivesList = archivesList
        if (archivesList.isNotEmpty()) {
            println("Выберите архив либо введите любой другой символ для возврата:")
            archivesList.forEachIndexed { index, archive ->
                println("$index — ${archive.name}")
            }
            while (true) {
                val selectedArchive = scanner.nextLine().toIntOrNull()
                if (selectedArchive != null && selectedArchive in archivesList.indices) {
                    val notes = NotesActions(archivesList, selectedArchive)
                    notes.selectNotesAction()
                    break
                } else {
                    selectAction()
                    break
                }
            }
        } else {
            println("Архивов пока нет, создайте хотя бы один")
            moreArchives(listOf(Actions.CREATE_ARCHIVE.action, Actions.BACK.action))
        }
    }

    private fun moreArchives(actions: List<String>) {
        actions.forEachIndexed { index, item ->
            println("$index — $item")
        }
        while (true) {
            val action = scanner.nextLine().toIntOrNull()
            when (action) {
                0 -> {
                    createArchive()
                    break
                }

                1 -> {
                    selectAction()
                    break
                }

                else -> println(Actions.TRY_AGAIN.action)
            }
        }
    }
}