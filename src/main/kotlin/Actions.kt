enum class Actions(val action: String) {
    SELECT_ACTION("Выберите действие:"),
    CREATE_ARCHIVE("Создать архив"),
    SELECT_ARCHIVE("Выбрать архив"),
    EXIT("Выйти"),
    CREATE_NOTE("Создать заметку"),
    OPEN_NOTES("Просмотр заметок"),
    TRY_AGAIN("Выберите один из вариантов списка"),
    MORE("Добавить ещё"),
    BACK("Назад")
}