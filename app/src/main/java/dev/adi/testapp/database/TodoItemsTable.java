package dev.adi.testapp.database;

public class TodoItemsTable {

    public static final String TABLE_NAME = "todoItems";
    public static final String COLUMN_ID = "todoId";
    public static final String COLUMN_NAME = "todoName";
    public static final String COLUMN_GROUP = "todoGroup";
    public static final String COLUMN_POSITION = "todoSortPosition";
    public static final String COLUMN_COMPLETED = "todoCompleted";
    public static final String COLUMN_DELETED = "todoDeleted";

    public static final String[] ALL_COLUMNS = { COLUMN_ID, COLUMN_NAME, COLUMN_GROUP, COLUMN_POSITION, COLUMN_COMPLETED, COLUMN_DELETED };

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " TEXT PRIMARY KEY NOT NULL," +
                    COLUMN_NAME + " TEXT NOT NULL," +
                    COLUMN_GROUP + " TEXT," +
                    COLUMN_POSITION + " INTEGER," +
                    COLUMN_COMPLETED + " INTEGER," +
                    COLUMN_DELETED + " INTEGER" + ");";

    public static final String SQL_DELETE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
