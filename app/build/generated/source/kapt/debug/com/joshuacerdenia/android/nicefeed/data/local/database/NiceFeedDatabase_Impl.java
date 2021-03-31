package com.joshuacerdenia.android.nicefeed.data.local.database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class NiceFeedDatabase_Impl extends NiceFeedDatabase {
  private volatile CombinedDao _combinedDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Feed` (`url` TEXT NOT NULL, `title` TEXT NOT NULL, `website` TEXT NOT NULL, `description` TEXT, `imageUrl` TEXT, `category` TEXT NOT NULL, `unreadCount` INTEGER NOT NULL, PRIMARY KEY(`url`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Entry` (`url` TEXT NOT NULL, `title` TEXT NOT NULL, `website` TEXT NOT NULL, `author` TEXT, `date` INTEGER, `content` TEXT, `image` TEXT, `isStarred` INTEGER NOT NULL, `isRead` INTEGER NOT NULL, PRIMARY KEY(`url`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `FeedEntryCrossRef` (`feedUrl` TEXT NOT NULL, `entryUrl` TEXT NOT NULL, PRIMARY KEY(`feedUrl`, `entryUrl`))");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_FeedEntryCrossRef_entryUrl` ON `FeedEntryCrossRef` (`entryUrl`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'a5eb0b5c94bd7c9509cf8032d817efba')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Feed`");
        _db.execSQL("DROP TABLE IF EXISTS `Entry`");
        _db.execSQL("DROP TABLE IF EXISTS `FeedEntryCrossRef`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsFeed = new HashMap<String, TableInfo.Column>(7);
        _columnsFeed.put("url", new TableInfo.Column("url", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFeed.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFeed.put("website", new TableInfo.Column("website", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFeed.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFeed.put("imageUrl", new TableInfo.Column("imageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFeed.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFeed.put("unreadCount", new TableInfo.Column("unreadCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFeed = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFeed = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFeed = new TableInfo("Feed", _columnsFeed, _foreignKeysFeed, _indicesFeed);
        final TableInfo _existingFeed = TableInfo.read(_db, "Feed");
        if (! _infoFeed.equals(_existingFeed)) {
          return new RoomOpenHelper.ValidationResult(false, "Feed(com.joshuacerdenia.android.nicefeed.data.model.feed.Feed).\n"
                  + " Expected:\n" + _infoFeed + "\n"
                  + " Found:\n" + _existingFeed);
        }
        final HashMap<String, TableInfo.Column> _columnsEntry = new HashMap<String, TableInfo.Column>(9);
        _columnsEntry.put("url", new TableInfo.Column("url", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEntry.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEntry.put("website", new TableInfo.Column("website", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEntry.put("author", new TableInfo.Column("author", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEntry.put("date", new TableInfo.Column("date", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEntry.put("content", new TableInfo.Column("content", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEntry.put("image", new TableInfo.Column("image", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEntry.put("isStarred", new TableInfo.Column("isStarred", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEntry.put("isRead", new TableInfo.Column("isRead", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEntry = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEntry = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoEntry = new TableInfo("Entry", _columnsEntry, _foreignKeysEntry, _indicesEntry);
        final TableInfo _existingEntry = TableInfo.read(_db, "Entry");
        if (! _infoEntry.equals(_existingEntry)) {
          return new RoomOpenHelper.ValidationResult(false, "Entry(com.joshuacerdenia.android.nicefeed.data.model.entry.Entry).\n"
                  + " Expected:\n" + _infoEntry + "\n"
                  + " Found:\n" + _existingEntry);
        }
        final HashMap<String, TableInfo.Column> _columnsFeedEntryCrossRef = new HashMap<String, TableInfo.Column>(2);
        _columnsFeedEntryCrossRef.put("feedUrl", new TableInfo.Column("feedUrl", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFeedEntryCrossRef.put("entryUrl", new TableInfo.Column("entryUrl", "TEXT", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFeedEntryCrossRef = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFeedEntryCrossRef = new HashSet<TableInfo.Index>(1);
        _indicesFeedEntryCrossRef.add(new TableInfo.Index("index_FeedEntryCrossRef_entryUrl", false, Arrays.asList("entryUrl")));
        final TableInfo _infoFeedEntryCrossRef = new TableInfo("FeedEntryCrossRef", _columnsFeedEntryCrossRef, _foreignKeysFeedEntryCrossRef, _indicesFeedEntryCrossRef);
        final TableInfo _existingFeedEntryCrossRef = TableInfo.read(_db, "FeedEntryCrossRef");
        if (! _infoFeedEntryCrossRef.equals(_existingFeedEntryCrossRef)) {
          return new RoomOpenHelper.ValidationResult(false, "FeedEntryCrossRef(com.joshuacerdenia.android.nicefeed.data.model.cross.FeedEntryCrossRef).\n"
                  + " Expected:\n" + _infoFeedEntryCrossRef + "\n"
                  + " Found:\n" + _existingFeedEntryCrossRef);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "a5eb0b5c94bd7c9509cf8032d817efba", "2ffdba029a839fc22eccb2fd621c03c2");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "Feed","Entry","FeedEntryCrossRef");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `Feed`");
      _db.execSQL("DELETE FROM `Entry`");
      _db.execSQL("DELETE FROM `FeedEntryCrossRef`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public CombinedDao combinedDao() {
    if (_combinedDao != null) {
      return _combinedDao;
    } else {
      synchronized(this) {
        if(_combinedDao == null) {
          _combinedDao = new CombinedDao_Impl(this);
        }
        return _combinedDao;
      }
    }
  }
}
