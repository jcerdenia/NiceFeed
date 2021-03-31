package com.joshuacerdenia.android.nicefeed.data.local.database;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.joshuacerdenia.android.nicefeed.data.model.cross.FeedEntryCrossRef;
import com.joshuacerdenia.android.nicefeed.data.model.cross.FeedTitleWithEntriesToggleable;
import com.joshuacerdenia.android.nicefeed.data.model.entry.Entry;
import com.joshuacerdenia.android.nicefeed.data.model.entry.EntryToggleable;
import com.joshuacerdenia.android.nicefeed.data.model.feed.Feed;
import com.joshuacerdenia.android.nicefeed.data.model.feed.FeedIdWithCategory;
import com.joshuacerdenia.android.nicefeed.data.model.feed.FeedLight;
import com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class CombinedDao_Impl implements CombinedDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Entry> __insertionAdapterOfEntry;

  private final TypeConverters __typeConverters = new TypeConverters();

  private final EntityInsertionAdapter<FeedEntryCrossRef> __insertionAdapterOfFeedEntryCrossRef;

  private final EntityInsertionAdapter<Feed> __insertionAdapterOfFeed;

  private final EntityDeletionOrUpdateAdapter<Entry> __deletionAdapterOfEntry;

  private final EntityDeletionOrUpdateAdapter<Entry> __updateAdapterOfEntry;

  private final EntityDeletionOrUpdateAdapter<Feed> __updateAdapterOfFeed;

  private final SharedSQLiteStatement __preparedStmtOfDeleteLeftoverEntries;

  private final SharedSQLiteStatement __preparedStmtOfDeleteLeftoverCrossRefs;

  private final SharedSQLiteStatement __preparedStmtOfUpdateFeedTitle;

  private final SharedSQLiteStatement __preparedStmtOfUpdateFeedImage;

  private final SharedSQLiteStatement __preparedStmtOfUpdateFeedUnreadCount;

  private final SharedSQLiteStatement __preparedStmtOfAddToFeedUnreadCount;

  private final SharedSQLiteStatement __preparedStmtOfAddToFeedUnreadCountByEntry;

  public CombinedDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEntry = new EntityInsertionAdapter<Entry>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `Entry` (`url`,`title`,`website`,`author`,`date`,`content`,`image`,`isStarred`,`isRead`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Entry value) {
        if (value.getUrl() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUrl());
        }
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getWebsite() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getWebsite());
        }
        if (value.getAuthor() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAuthor());
        }
        final Long _tmp;
        _tmp = __typeConverters.fromDate(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp);
        }
        if (value.getContent() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getContent());
        }
        if (value.getImage() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getImage());
        }
        final int _tmp_1;
        _tmp_1 = value.isStarred() ? 1 : 0;
        stmt.bindLong(8, _tmp_1);
        final int _tmp_2;
        _tmp_2 = value.isRead() ? 1 : 0;
        stmt.bindLong(9, _tmp_2);
      }
    };
    this.__insertionAdapterOfFeedEntryCrossRef = new EntityInsertionAdapter<FeedEntryCrossRef>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `FeedEntryCrossRef` (`feedUrl`,`entryUrl`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FeedEntryCrossRef value) {
        if (value.getFeedUrl() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getFeedUrl());
        }
        if (value.getEntryUrl() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getEntryUrl());
        }
      }
    };
    this.__insertionAdapterOfFeed = new EntityInsertionAdapter<Feed>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `Feed` (`url`,`title`,`website`,`description`,`imageUrl`,`category`,`unreadCount`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Feed value) {
        if (value.getUrl() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUrl());
        }
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getWebsite() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getWebsite());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDescription());
        }
        if (value.getImageUrl() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getImageUrl());
        }
        if (value.getCategory() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCategory());
        }
        stmt.bindLong(7, value.getUnreadCount());
      }
    };
    this.__deletionAdapterOfEntry = new EntityDeletionOrUpdateAdapter<Entry>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Entry` WHERE `url` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Entry value) {
        if (value.getUrl() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUrl());
        }
      }
    };
    this.__updateAdapterOfEntry = new EntityDeletionOrUpdateAdapter<Entry>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Entry` SET `url` = ?,`title` = ?,`website` = ?,`author` = ?,`date` = ?,`content` = ?,`image` = ?,`isStarred` = ?,`isRead` = ? WHERE `url` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Entry value) {
        if (value.getUrl() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUrl());
        }
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getWebsite() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getWebsite());
        }
        if (value.getAuthor() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAuthor());
        }
        final Long _tmp;
        _tmp = __typeConverters.fromDate(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp);
        }
        if (value.getContent() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getContent());
        }
        if (value.getImage() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getImage());
        }
        final int _tmp_1;
        _tmp_1 = value.isStarred() ? 1 : 0;
        stmt.bindLong(8, _tmp_1);
        final int _tmp_2;
        _tmp_2 = value.isRead() ? 1 : 0;
        stmt.bindLong(9, _tmp_2);
        if (value.getUrl() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getUrl());
        }
      }
    };
    this.__updateAdapterOfFeed = new EntityDeletionOrUpdateAdapter<Feed>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Feed` SET `url` = ?,`title` = ?,`website` = ?,`description` = ?,`imageUrl` = ?,`category` = ?,`unreadCount` = ? WHERE `url` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Feed value) {
        if (value.getUrl() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUrl());
        }
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getWebsite() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getWebsite());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDescription());
        }
        if (value.getImageUrl() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getImageUrl());
        }
        if (value.getCategory() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCategory());
        }
        stmt.bindLong(7, value.getUnreadCount());
        if (value.getUrl() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getUrl());
        }
      }
    };
    this.__preparedStmtOfDeleteLeftoverEntries = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM Entry WHERE url NOT IN (SELECT entryUrl FROM FeedEntryCrossRef)";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteLeftoverCrossRefs = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM FeedEntryCrossRef WHERE feedUrl NOT IN (SELECT url FROM Feed)";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateFeedTitle = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE Feed SET title = ? WHERE url = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateFeedImage = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE Feed SET imageUrl = ? WHERE url = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateFeedUnreadCount = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE Feed SET unreadCount = ? WHERE url = ?";
        return _query;
      }
    };
    this.__preparedStmtOfAddToFeedUnreadCount = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE Feed SET unreadCount = (unreadCount + ?) WHERE url = ?";
        return _query;
      }
    };
    this.__preparedStmtOfAddToFeedUnreadCountByEntry = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE Feed SET unreadCount = (unreadCount + ?) WHERE url IN (SELECT url FROM FeedEntryCrossRef AS _junction INNER JOIN Feed ON (_junction.feedUrl = Feed.url) WHERE _junction.entryUrl = (?))";
        return _query;
      }
    };
  }

  @Override
  public void addEntries(final List<Entry> entries) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfEntry.insert(entries);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void addFeedEntryCrossRefs(final List<FeedEntryCrossRef> crossRefs) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfFeedEntryCrossRef.insert(crossRefs);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void addFeeds(final Feed... feed) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfFeed.insert(feed);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteEntries(final List<Entry> entries) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfEntry.handleMultiple(entries);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateEntries(final List<Entry> entries) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfEntry.handleMultiple(entries);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateFeed(final Feed feed) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfFeed.handle(feed);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void addFeedEntryCrossRefs(final String feedId, final List<Entry> entries) {
    __db.beginTransaction();
    try {
      CombinedDao.DefaultImpls.addFeedEntryCrossRefs(CombinedDao_Impl.this, feedId, entries);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateFeedTitleAndCategory(final String feedId, final String title,
      final String category) {
    __db.beginTransaction();
    try {
      CombinedDao.DefaultImpls.updateFeedTitleAndCategory(CombinedDao_Impl.this, feedId, title, category);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void addFeedAndEntries(final Feed feed, final List<Entry> entries) {
    __db.beginTransaction();
    try {
      CombinedDao.DefaultImpls.addFeedAndEntries(CombinedDao_Impl.this, feed, entries);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public FeedTitleWithEntriesToggleable getFeedTitleAndEntriesToggleableSynchronously(
      final String feedId) {
    __db.beginTransaction();
    try {
      FeedTitleWithEntriesToggleable _result = CombinedDao.DefaultImpls.getFeedTitleAndEntriesToggleableSynchronously(CombinedDao_Impl.this, feedId);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void handleEntryUpdates(final String feedId, final List<Entry> entriesToAdd,
      final List<Entry> entriesToUpdate, final List<Entry> entriesToDelete) {
    __db.beginTransaction();
    try {
      CombinedDao.DefaultImpls.handleEntryUpdates(CombinedDao_Impl.this, feedId, entriesToAdd, entriesToUpdate, entriesToDelete);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void handleBackgroundUpdate(final String feedId, final List<Entry> newEntries,
      final List<EntryToggleable> oldEntries, final String feedImage) {
    __db.beginTransaction();
    try {
      CombinedDao.DefaultImpls.handleBackgroundUpdate(CombinedDao_Impl.this, feedId, newEntries, oldEntries, feedImage);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateEntryAndFeedUnreadCount(final String entryId, final boolean isRead,
      final boolean isStarred) {
    __db.beginTransaction();
    try {
      CombinedDao.DefaultImpls.updateEntryAndFeedUnreadCount(CombinedDao_Impl.this, entryId, isRead, isStarred);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateEntryIsReadAndFeedUnreadCount(final String[] entryId, final boolean isRead) {
    __db.beginTransaction();
    try {
      CombinedDao.DefaultImpls.updateEntryIsReadAndFeedUnreadCount(CombinedDao_Impl.this, entryId, isRead);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteFeedAndEntriesById(final String... feedId) {
    __db.beginTransaction();
    try {
      CombinedDao.DefaultImpls.deleteFeedAndEntriesById(CombinedDao_Impl.this, feedId);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteLeftoverItems() {
    __db.beginTransaction();
    try {
      CombinedDao.DefaultImpls.deleteLeftoverItems(CombinedDao_Impl.this);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteLeftoverEntries() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteLeftoverEntries.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteLeftoverEntries.release(_stmt);
    }
  }

  @Override
  public void deleteLeftoverCrossRefs() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteLeftoverCrossRefs.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteLeftoverCrossRefs.release(_stmt);
    }
  }

  @Override
  public void updateFeedTitle(final String feedId, final String title) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateFeedTitle.acquire();
    int _argIndex = 1;
    if (title == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, title);
    }
    _argIndex = 2;
    if (feedId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, feedId);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateFeedTitle.release(_stmt);
    }
  }

  @Override
  public void updateFeedImage(final String feedId, final String feedImage) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateFeedImage.acquire();
    int _argIndex = 1;
    if (feedImage == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, feedImage);
    }
    _argIndex = 2;
    if (feedId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, feedId);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateFeedImage.release(_stmt);
    }
  }

  @Override
  public void updateFeedUnreadCount(final String feedId, final int count) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateFeedUnreadCount.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, count);
    _argIndex = 2;
    if (feedId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, feedId);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateFeedUnreadCount.release(_stmt);
    }
  }

  @Override
  public void addToFeedUnreadCount(final String feedId, final int addend) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfAddToFeedUnreadCount.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, addend);
    _argIndex = 2;
    if (feedId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, feedId);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfAddToFeedUnreadCount.release(_stmt);
    }
  }

  @Override
  public void addToFeedUnreadCountByEntry(final String entryId, final int addend) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfAddToFeedUnreadCountByEntry.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, addend);
    _argIndex = 2;
    if (entryId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, entryId);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfAddToFeedUnreadCountByEntry.release(_stmt);
    }
  }

  @Override
  public LiveData<Entry> getEntry(final String entryId) {
    final String _sql = "SELECT * FROM Entry WHERE url = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (entryId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, entryId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"Entry"}, false, new Callable<Entry>() {
      @Override
      public Entry call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfWebsite = CursorUtil.getColumnIndexOrThrow(_cursor, "website");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
          final int _cursorIndexOfIsStarred = CursorUtil.getColumnIndexOrThrow(_cursor, "isStarred");
          final int _cursorIndexOfIsRead = CursorUtil.getColumnIndexOrThrow(_cursor, "isRead");
          final Entry _result;
          if(_cursor.moveToFirst()) {
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpWebsite;
            _tmpWebsite = _cursor.getString(_cursorIndexOfWebsite);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = __typeConverters.toDate(_tmp);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final String _tmpImage;
            _tmpImage = _cursor.getString(_cursorIndexOfImage);
            final boolean _tmpIsStarred;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsStarred);
            _tmpIsStarred = _tmp_1 != 0;
            final boolean _tmpIsRead;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsRead);
            _tmpIsRead = _tmp_2 != 0;
            _result = new Entry(_tmpUrl,_tmpTitle,_tmpWebsite,_tmpAuthor,_tmpDate,_tmpContent,_tmpImage,_tmpIsStarred,_tmpIsRead);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Entry>> getNewEntries(final int max) {
    final String _sql = "SELECT url, title, website, date, image, isStarred, isRead FROM Entry WHERE isRead = 0 ORDER BY date DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, max);
    return __db.getInvalidationTracker().createLiveData(new String[]{"Entry"}, false, new Callable<List<Entry>>() {
      @Override
      public List<Entry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfWebsite = CursorUtil.getColumnIndexOrThrow(_cursor, "website");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
          final int _cursorIndexOfIsStarred = CursorUtil.getColumnIndexOrThrow(_cursor, "isStarred");
          final int _cursorIndexOfIsRead = CursorUtil.getColumnIndexOrThrow(_cursor, "isRead");
          final List<Entry> _result = new ArrayList<Entry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Entry _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpWebsite;
            _tmpWebsite = _cursor.getString(_cursorIndexOfWebsite);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = __typeConverters.toDate(_tmp);
            final String _tmpImage;
            _tmpImage = _cursor.getString(_cursorIndexOfImage);
            final boolean _tmpIsStarred;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsStarred);
            _tmpIsStarred = _tmp_1 != 0;
            final boolean _tmpIsRead;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsRead);
            _tmpIsRead = _tmp_2 != 0;
            _item = new Entry(_tmpUrl,_tmpTitle,_tmpWebsite,null,_tmpDate,null,_tmpImage,_tmpIsStarred,_tmpIsRead);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Entry>> getStarredEntries() {
    final String _sql = "SELECT url, title, website, date, image, isStarred, isRead FROM Entry WHERE isStarred = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"Entry"}, false, new Callable<List<Entry>>() {
      @Override
      public List<Entry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfWebsite = CursorUtil.getColumnIndexOrThrow(_cursor, "website");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
          final int _cursorIndexOfIsStarred = CursorUtil.getColumnIndexOrThrow(_cursor, "isStarred");
          final int _cursorIndexOfIsRead = CursorUtil.getColumnIndexOrThrow(_cursor, "isRead");
          final List<Entry> _result = new ArrayList<Entry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Entry _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpWebsite;
            _tmpWebsite = _cursor.getString(_cursorIndexOfWebsite);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = __typeConverters.toDate(_tmp);
            final String _tmpImage;
            _tmpImage = _cursor.getString(_cursorIndexOfImage);
            final boolean _tmpIsStarred;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsStarred);
            _tmpIsStarred = _tmp_1 != 0;
            final boolean _tmpIsRead;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsRead);
            _tmpIsRead = _tmp_2 != 0;
            _item = new Entry(_tmpUrl,_tmpTitle,_tmpWebsite,null,_tmpDate,null,_tmpImage,_tmpIsStarred,_tmpIsRead);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Entry>> getEntriesByFeed(final String feedId) {
    final String _sql = "SELECT Entry.url, title, website, author, date, content, image, isStarred, isRead FROM FeedEntryCrossRef AS _junction INNER JOIN Entry ON (_junction.entryUrl = Entry.url) WHERE _junction.feedUrl = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (feedId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, feedId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"FeedEntryCrossRef","Entry"}, false, new Callable<List<Entry>>() {
      @Override
      public List<Entry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfWebsite = CursorUtil.getColumnIndexOrThrow(_cursor, "website");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
          final int _cursorIndexOfIsStarred = CursorUtil.getColumnIndexOrThrow(_cursor, "isStarred");
          final int _cursorIndexOfIsRead = CursorUtil.getColumnIndexOrThrow(_cursor, "isRead");
          final List<Entry> _result = new ArrayList<Entry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Entry _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpWebsite;
            _tmpWebsite = _cursor.getString(_cursorIndexOfWebsite);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = __typeConverters.toDate(_tmp);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final String _tmpImage;
            _tmpImage = _cursor.getString(_cursorIndexOfImage);
            final boolean _tmpIsStarred;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsStarred);
            _tmpIsStarred = _tmp_1 != 0;
            final boolean _tmpIsRead;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsRead);
            _tmpIsRead = _tmp_2 != 0;
            _item = new Entry(_tmpUrl,_tmpTitle,_tmpWebsite,_tmpAuthor,_tmpDate,_tmpContent,_tmpImage,_tmpIsStarred,_tmpIsRead);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<EntryToggleable> getEntriesToggleableByFeedSynchronously(final String feedId) {
    final String _sql = "SELECT Entry.url, isStarred, isRead FROM FeedEntryCrossRef AS _junction INNER JOIN Entry ON (_junction.entryUrl = Entry.url) WHERE _junction.feedUrl = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (feedId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, feedId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
      final int _cursorIndexOfIsStarred = CursorUtil.getColumnIndexOrThrow(_cursor, "isStarred");
      final int _cursorIndexOfIsRead = CursorUtil.getColumnIndexOrThrow(_cursor, "isRead");
      final List<EntryToggleable> _result = new ArrayList<EntryToggleable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final EntryToggleable _item;
        final String _tmpUrl;
        _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        final boolean _tmpIsStarred;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsStarred);
        _tmpIsStarred = _tmp != 0;
        final boolean _tmpIsRead;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsRead);
        _tmpIsRead = _tmp_1 != 0;
        _item = new EntryToggleable(_tmpUrl,_tmpIsStarred,_tmpIsRead);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Feed> getFeed(final String feedId) {
    final String _sql = "SELECT * FROM Feed WHERE url = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (feedId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, feedId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"Feed"}, false, new Callable<Feed>() {
      @Override
      public Feed call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfWebsite = CursorUtil.getColumnIndexOrThrow(_cursor, "website");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfUnreadCount = CursorUtil.getColumnIndexOrThrow(_cursor, "unreadCount");
          final Feed _result;
          if(_cursor.moveToFirst()) {
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpWebsite;
            _tmpWebsite = _cursor.getString(_cursorIndexOfWebsite);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpImageUrl;
            _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final int _tmpUnreadCount;
            _tmpUnreadCount = _cursor.getInt(_cursorIndexOfUnreadCount);
            _result = new Feed(_tmpUrl,_tmpTitle,_tmpWebsite,_tmpDescription,_tmpImageUrl,_tmpCategory,_tmpUnreadCount);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<FeedLight>> getFeedsLight() {
    final String _sql = "SELECT url, title, imageUrl, category, unreadCount FROM Feed";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"Feed"}, false, new Callable<List<FeedLight>>() {
      @Override
      public List<FeedLight> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfUnreadCount = CursorUtil.getColumnIndexOrThrow(_cursor, "unreadCount");
          final List<FeedLight> _result = new ArrayList<FeedLight>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final FeedLight _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpImageUrl;
            _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final int _tmpUnreadCount;
            _tmpUnreadCount = _cursor.getInt(_cursorIndexOfUnreadCount);
            _item = new FeedLight(_tmpUrl,_tmpTitle,_tmpImageUrl,_tmpCategory,_tmpUnreadCount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<FeedManageable>> getFeedsManageable() {
    final String _sql = "SELECT url, title, website, imageUrl, description, category FROM Feed";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"Feed"}, false, new Callable<List<FeedManageable>>() {
      @Override
      public List<FeedManageable> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfWebsite = CursorUtil.getColumnIndexOrThrow(_cursor, "website");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final List<FeedManageable> _result = new ArrayList<FeedManageable>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final FeedManageable _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpWebsite;
            _tmpWebsite = _cursor.getString(_cursorIndexOfWebsite);
            final String _tmpImageUrl;
            _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            _item = new FeedManageable(_tmpUrl,_tmpTitle,_tmpWebsite,_tmpImageUrl,_tmpDescription,_tmpCategory);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<String>> getFeedIds() {
    final String _sql = "SELECT url FROM Feed";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"Feed"}, false, new Callable<List<String>>() {
      @Override
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final String _item;
            _item = _cursor.getString(0);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<FeedIdWithCategory>> getFeedIdsWithCategories() {
    final String _sql = "SELECT url, category FROM Feed";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"Feed"}, false, new Callable<List<FeedIdWithCategory>>() {
      @Override
      public List<FeedIdWithCategory> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final List<FeedIdWithCategory> _result = new ArrayList<FeedIdWithCategory>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final FeedIdWithCategory _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            _item = new FeedIdWithCategory(_tmpUrl,_tmpCategory);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<String> getFeedUrlsSynchronously() {
    final String _sql = "SELECT url FROM Feed";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final List<String> _result = new ArrayList<String>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final String _item;
        _item = _cursor.getString(0);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public String getFeedTitleSynchronously(final String feedId) {
    final String _sql = "SELECT title FROM Feed WHERE url = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (feedId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, feedId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final String _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getString(0);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public void updateEntryIsStarred(final String[] entryId, final boolean isStarred) {
    __db.assertNotSuspendingTransaction();
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("UPDATE Entry SET isStarred = ");
    _stringBuilder.append("?");
    _stringBuilder.append(" WHERE url IN (");
    final int _inputSize = entryId.length;
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final SupportSQLiteStatement _stmt = __db.compileStatement(_sql);
    int _argIndex = 1;
    final int _tmp;
    _tmp = isStarred ? 1 : 0;
    _stmt.bindLong(_argIndex, _tmp);
    _argIndex = 2;
    for (String _item : entryId) {
      if (_item == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, _item);
      }
      _argIndex ++;
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateEntryIsRead(final String[] entryId, final boolean isRead) {
    __db.assertNotSuspendingTransaction();
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("UPDATE Entry SET isRead = ");
    _stringBuilder.append("?");
    _stringBuilder.append(" WHERE url IN (");
    final int _inputSize = entryId.length;
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final SupportSQLiteStatement _stmt = __db.compileStatement(_sql);
    int _argIndex = 1;
    final int _tmp;
    _tmp = isRead ? 1 : 0;
    _stmt.bindLong(_argIndex, _tmp);
    _argIndex = 2;
    for (String _item : entryId) {
      if (_item == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, _item);
      }
      _argIndex ++;
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteEntriesByFeed(final String... feedId) {
    __db.assertNotSuspendingTransaction();
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("DELETE FROM Entry WHERE url IN (SELECT url FROM FeedEntryCrossRef AS _junction INNER JOIN Entry ON (_junction.entryUrl = Entry.url) WHERE _junction.feedUrl IN (");
    final int _inputSize = feedId.length;
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append("))");
    final String _sql = _stringBuilder.toString();
    final SupportSQLiteStatement _stmt = __db.compileStatement(_sql);
    int _argIndex = 1;
    for (String _item : feedId) {
      if (_item == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, _item);
      }
      _argIndex ++;
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteEntriesById(final List<String> entryIds) {
    __db.assertNotSuspendingTransaction();
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("DELETE FROM Entry WHERE url IN (");
    final int _inputSize = entryIds.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final SupportSQLiteStatement _stmt = __db.compileStatement(_sql);
    int _argIndex = 1;
    for (String _item : entryIds) {
      if (_item == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, _item);
      }
      _argIndex ++;
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteFeedEntryCrossRefs(final String feedId, final List<String> entryIds) {
    __db.assertNotSuspendingTransaction();
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("DELETE FROM FeedEntryCrossRef WHERE feedUrl = ");
    _stringBuilder.append("?");
    _stringBuilder.append(" AND entryUrl IN (");
    final int _inputSize = entryIds.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final SupportSQLiteStatement _stmt = __db.compileStatement(_sql);
    int _argIndex = 1;
    if (feedId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, feedId);
    }
    _argIndex = 2;
    for (String _item : entryIds) {
      if (_item == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, _item);
      }
      _argIndex ++;
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteCrossRefsByFeed(final String... feedId) {
    __db.assertNotSuspendingTransaction();
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("DELETE FROM FeedEntryCrossRef WHERE feedUrl IN (");
    final int _inputSize = feedId.length;
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final SupportSQLiteStatement _stmt = __db.compileStatement(_sql);
    int _argIndex = 1;
    for (String _item : feedId) {
      if (_item == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, _item);
      }
      _argIndex ++;
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateFeedCategory(final String[] feedId, final String category) {
    __db.assertNotSuspendingTransaction();
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("UPDATE Feed SET category = ");
    _stringBuilder.append("?");
    _stringBuilder.append(" WHERE url IN (");
    final int _inputSize = feedId.length;
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final SupportSQLiteStatement _stmt = __db.compileStatement(_sql);
    int _argIndex = 1;
    if (category == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, category);
    }
    _argIndex = 2;
    for (String _item : feedId) {
      if (_item == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, _item);
      }
      _argIndex ++;
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteFeeds(final String... feedId) {
    __db.assertNotSuspendingTransaction();
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("DELETE FROM Feed WHERE url IN (");
    final int _inputSize = feedId.length;
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final SupportSQLiteStatement _stmt = __db.compileStatement(_sql);
    int _argIndex = 1;
    for (String _item : feedId) {
      if (_item == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, _item);
      }
      _argIndex ++;
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }
}
