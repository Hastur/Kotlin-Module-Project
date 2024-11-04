import entities.Archive
import entities.Note
import java.util.Scanner

class NotesActions {
    private val scanner = Scanner(System.`in`)

    fun selectNotesAction(archive: Archive) {
        val actionsList = listOf(
            Actions.CREATE_NOTE.action,
            Actions.OPEN_NOTES.action,
            Actions.BACK.action
        )

        println("Вы находитесь в архиве «${archive.name}»")
        actionsList.forEachIndexed { index, action ->
            println("$index — $action")
        }
        while (true) {
            val action = scanner.nextLine().toIntOrNull()
            when (action) {
                0 -> {
                    createNote(archive)
                    break
                }

                1 -> {
                    openNotesList(archive)
                    break
                }

                2 -> {
                    ArchivesActions().selectArchive()
                    break
                }

                else -> println(Actions.TRY_AGAIN.action)
            }
        }
    }

    private fun createNote(archive: Archive) {
        var name: String?
        var content: String?
        println("Введите название заметки:")
        while (true) {
            name = scanner.nextLine()
            if (name.trim().isEmpty()) println("Название не может быть пустым") else break
        }
        println("Введите текст заметки:")
        while (true) {
            content = scanner.nextLine()
            if (content.trim().isEmpty()) println("Текст не может быть пустым") else break
        }
        if (name != null && content != null) {
            archive.notes.add(Note(name, content))
            println("Заметка добавлена в архив")
            moreNotes(listOf(Actions.MORE.action, Actions.BACK.action), archive)
        }
    }

    private fun openNotesList(archive: Archive) {
        if (archive.notes.isNotEmpty()) {
            println("Заметки архива «${archive.name}»")
            println("Выберите заметку либо введите любой другой символ для возврата:")
            archive.notes.forEachIndexed { index, note ->
                println("$index — ${note.name}")
            }
            while (true) {
                val note = scanner.nextLine().toIntOrNull()
                if (note != null && note in archive.notes.indices) {
                    openNote(archive, note)
                    break
                } else {
                    selectNotesAction(archive)
                    break
                }
            }
        } else {
            println("В этом архиве нет заметок, создайте хотя бы одну")
            moreNotes(listOf(Actions.CREATE_NOTE.action, Actions.BACK.action), archive)
        }
    }

    private fun openNote(archive: Archive, noteIndex: Int) {
        println("Содержимое заметки «${archive.notes[noteIndex].name}»:")
        println(archive.notes[noteIndex].content)
        println("\nНажмите любую клавишу для возврата")
        val back = scanner.nextLine()
        if (back != null) openNotesList(archive)
    }

    private fun moreNotes(actions: List<String>, archive: Archive) {
        actions.forEachIndexed { index, item ->
            println("$index — $item")
        }
        while (true) {
            val action = scanner.nextLine().toIntOrNull()
            when (action) {
                0 -> {
                    createNote(archive)
                    break
                }

                1 -> {
                    if (archive.notes.isNotEmpty()) openNotesList(archive)
                    else selectNotesAction(archive)
                    break
                }

                else -> println(Actions.TRY_AGAIN.action)
            }
        }
    }
}