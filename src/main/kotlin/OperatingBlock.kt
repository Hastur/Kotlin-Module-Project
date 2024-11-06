import entities.Actions
import entities.Archive
import entities.Note
import entities.Screens
import java.util.Scanner

class OperatingBlock(private val scanner: Scanner) {
    private val archivesList = mutableListOf<Archive>()
    private var currentScreen = Screens.ARCHIVES_SCREEN
    private var currentArchiveIndex = 0

    fun start() {
        when (currentScreen) {
            Screens.ARCHIVES_SCREEN -> {
                setRootMenu(
                    listOf(
                        Actions.CREATE_ARCHIVE.action,
                        Actions.SELECT_ARCHIVE.action,
                        Actions.EXIT.action
                    )
                )
            }

            Screens.NOTES_SCREEN -> {
                println("Вы находитесь в архиве «${archivesList[currentArchiveIndex].name}»")
                setRootMenu(
                    listOf(
                        Actions.CREATE_NOTE.action,
                        Actions.OPEN_NOTES.action,
                        Actions.BACK.action
                    )
                )
            }
        }
    }

    private fun setRootMenu(actionsList: List<String>) {
        println(Actions.SELECT_ACTION.action)

        actionsList.forEachIndexed { index, action ->
            println("$index — $action")
        }

        while (true) {
            val action = scanner.nextLine().toIntOrNull()
            when (action) {
                0 -> {
                    create()
                    break
                }

                1 -> {
                    select()
                    break
                }

                2 -> {
                    back()
                    break
                }

                else -> println(Actions.TRY_AGAIN.action)
            }
        }
    }

    private fun create() {
        println("Введите название:")
        val name = Utils().inputWithCheck()
        when (currentScreen) {
            Screens.ARCHIVES_SCREEN -> {
                archivesList.add(Archive(name, mutableListOf()))
                println("Архив «$name» добавлен")
                addMore()
            }

            Screens.NOTES_SCREEN -> {
                println("Введите текст заметки:")
                val content = Utils().inputWithCheck()
                archivesList[currentArchiveIndex].notes.add(Note(name, content))
                println("Заметка «$name» добавлена в архив «${archivesList[currentArchiveIndex].name}»")
                addMore()
            }
        }
    }

    private fun select() {
        val isEmpty = isEmptyCheck()
        if (!isEmpty) {
            when (currentScreen) {
                Screens.ARCHIVES_SCREEN -> {
                    println("Выберите архив либо введите любой другой символ для возврата:")
                    archivesList.forEachIndexed { index, archive ->
                        println("$index — ${archive.name}")
                    }
                    val archive = scanner.nextLine().toIntOrNull()
                    if (archive != null && archive in archivesList.indices) {
                        currentArchiveIndex = archive
                        currentScreen = Screens.NOTES_SCREEN
                    }
                    start()
                }

                Screens.NOTES_SCREEN -> {
                    println("Заметки архива «${archivesList[currentArchiveIndex].name}»")
                    println("Выберите заметку либо введите любой другой символ для возврата:")
                    archivesList[currentArchiveIndex].notes.forEachIndexed { index, note ->
                        println("$index — ${note.name}")
                    }
                    val note = scanner.nextLine().toIntOrNull()
                    if (note != null && note in archivesList[currentArchiveIndex].notes.indices) openNote(
                        archivesList[currentArchiveIndex].notes[note]
                    )
                    else start()
                }
            }
        }
    }

    private fun openNote(note: Note) {
        println("Содержимое заметки «${note.name}»:")
        println(note.content)
        println("\nВведите любой символ для возврата")
        val back = scanner.nextLine()
        if (back != null) select()
    }

    private fun back() {
        when (currentScreen) {
            Screens.ARCHIVES_SCREEN -> println("Выход из программы")
            Screens.NOTES_SCREEN -> {
                currentScreen = Screens.ARCHIVES_SCREEN
                select()
            }
        }
    }

    private fun isEmptyCheck(): Boolean {
        var isEmpty = false
        when (currentScreen) {
            Screens.ARCHIVES_SCREEN -> {
                isEmpty = archivesList.isEmpty()
                if (isEmpty) {
                    println("Архивов пока нет, создайте хотя бы один")
                    addMore(listOf(Actions.CREATE_ARCHIVE.action, Actions.BACK.action))
                }
            }

            Screens.NOTES_SCREEN -> {
                isEmpty = archivesList[currentArchiveIndex].notes.isEmpty()
                if (isEmpty) {
                    println("В этом архиве нет заметок, создайте хотя бы одну")
                    addMore(listOf(Actions.CREATE_NOTE.action, Actions.BACK.action))
                }
            }
        }
        return isEmpty
    }

    private fun addMore(actions: List<String> = listOf(Actions.MORE.action, Actions.BACK.action)) {
        actions.forEachIndexed { index, item ->
            println("$index — $item")
        }
        while (true) {
            val action = scanner.nextLine().toIntOrNull()
            when (action) {
                0 -> {
                    create()
                    break
                }

                1 -> {
                    start()
                    break
                }

                else -> println(Actions.TRY_AGAIN.action)
            }
        }
    }
}