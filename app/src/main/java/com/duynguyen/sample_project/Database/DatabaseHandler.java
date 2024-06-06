package com.duynguyen.sample_project.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.duynguyen.sample_project.R;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Library";
    private static final int DATABASE_VERSION = 20;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // CATEGORY CATEGORY CATEGORY
        String createCategory =
                "CREATE TABLE CATEGORY(" +
                        "categoryID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " categoryName TEXT NOT NULL," +
                        " categoryImage INTEGER," +
                        " categoryColor INTEGER)";
        db.execSQL(createCategory);

        String insertCategory = "INSERT INTO CATEGORY (categoryID, categoryName, categoryImage, categoryColor) VALUES" +
                "(1,'Pháp luật', " + R.mipmap.bg_law + ", " + R.color.cate1 + ")," +
                "(2, 'Công nghệ'," + R.mipmap.bg_technology + "," + R.color.cate2 + ")," +
                "(3, 'Văn học'," + R.mipmap.bg_literature + "," + R.color.cate3 + ")," +
                "(4, 'Khám phá'," + R.mipmap.bg_explore + "," + R.color.cate4 + ")," +
                "(5, 'Kỹ năng sống'," + R.mipmap.bg_skill + ", " + R.color.cate5 + ")," +
                "(6, 'Truyện tranh'," + R.mipmap.bg_comic + "," + R.color.cate6 + ")," +
                "(7, 'Khoa học'," + R.mipmap.bg_science + "," + R.color.cate11 + ")," +
                "(8, 'Tâm linh'," + R.mipmap.bg_spirituality + "," + R.color.cate9 + ")," +
                "(9, 'Thiên văn học'," + R.mipmap.bg_astronomy + "," + R.color.cate8 + ")," +
                "(10, 'Triết học'," + R.mipmap.bg_philosophy + "," + R.color.cate12 + ")";
        db.execSQL(insertCategory);



        // BOOK BOOK BOOK
        String createBookTable =
                "CREATE TABLE BOOK(" +
                        "bookID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " bookName TEXT NOT NULL," +
                        " bookImage TEXT NOT NULL," +
                        " description TEXT NOT NULL," +
                        " author TEXT NOT NULL," +
                        " inStock INTEGER NOT NULL," +
                        "categoryID INTEGER NOT NULL," +
                        "FOREIGN KEY (categoryID) REFERENCES CATEGORY(categoryID))";
        db.execSQL(createBookTable);

        String insertBook = "INSERT INTO BOOK (bookID, bookName, bookImage, description, author, inStock, categoryID) VALUES" +
                "(0, 'Đừng Để Nỗi Sợ Cản Đường Bạn', 'product3.jpg','Bởi nỗi sợ hãi là vô cùng phức tạp, nên ta sẽ không thể chỉ đơn giản là \"không sợ hãi\" hay \"buông bỏ\". Rốt cuộc, tất cả chúng ta đều phải đương đầu với những thử thách của cuộc sống bằng cả nỗi sợ hãi và lòng dũng cảm. Trở nên dũng cảm không phải là con đường thẳng tắp, mà nó được tích lũy dần dần khi đối mặt mới nỗi sợ hãi và những trở ngại. Khi chúng ta bắt đầu hiểu nỗi sợ hãi của mình và biết được chúng là thế nào, chúng ta có thể kiểm soát và tận dụng chúng. Khi chúng ta đã nhận biết, chấp nhận và hành động có mục đích đối mặt với nỗi sợ hãi, khi đó chúng ta sẽ trở nên am tường về nỗi sợ hãi', 'Helen Odessky', 10, 5)," +
                "(1, 'Điểm Số Không Phải Là Tất Cả', 'product4.jpg','Vậy nhưng “Điểm số không phải là tất cả”. Điểm số sẽ chỉ là một con số vô tri nếu các bạn học sinh không thể lĩnh hội được những kiến thức, rèn luyện tu dưỡng được những phẩm chất tốt đẹp để chuẩn bị cho dấu mốc quan trọng nhất - kỳ thi đại học cũng như cho cuộc sống tươi lai. So với việc cố gắng thúc ép bản thân, thúc ép con em mình, thúc ép học trò của mình đạt được những mốc điểm cao, việc nỗ lực không ngừng nghỉ kết hợp với một phương pháp học tập, phát triển bản thân hiệu quả, các bạn học sinh hoàn toàn có thể chạm đến những mục tiêu mà mình mong muốn và đặt chân vào những trường đại học chất lượng.', 'Chu Chính Minh - Lý Thừa Vận', 5, 5)," +
                "(2, 'Khéo Ăn Nói Sẽ Có Được Thiên Hạ (Tái Bản)','product5.jpg', 'Trong xã hội thông tin hiện đại, sự im lặng không còn là vàng nữa, nếu không biết cách giao tiếp thì dù là vàng cũng sẽ bị chôn vùi. Trong cuộc đời một con người, từ xin việc đến thăng tiến, từ tình yêu đến hôn nhân, từ tiếp thị cho đến đàm phán, từ xã giao đến làm việc… không thể không cần đến kĩ năng và khả năng giao tiếp. Khéo ăn khéo nói thì đi đâu, làm gì cũng gặp thuận lợi. Không khéo ăn nói, bốn bề đều là trở ngại khó khăn.', 'Trác Nhã', 8, 6)," +
                "(3, 'Mỗi Lần Vấp Ngã Là Một Lần Trưởng Thành (Tái Bản)','product6.jpg', 'Trong cuộc đời, mỗi chúng ta dù ít dù nhiều đều đã từng trải qua những thời khắc khó khăn, đau khổ. Đặc biệt đối với những bạn rời xa vòng tay che chở, bao bọc của cha mẹ và nhà trường, bước chân vào xã hội, bạn sẽ gặp phải rất nhiều trở ngại và nhận ra xã hội ngày vốn không hề đơn giản như bạn tưởng tượng.', 'Liêu Trí Phong', 8, 6), " +
                "(4, 'Tư duy pháp lý của luật sư','product7.jpg', 'Phần một: Giới thiệu với bạn về tư duy pháp lý và đưa ra các điều kiện bạn phải có, hay phải thay đổi so với trước kia để có thể có tư duy pháp lý. Tốt nghiệp trường luật xong bạn chưa có khả năng tư duy pháp lý để làm luật sư; vì trường luật đào tạo bạn làm cán bộ pháp chế (tức là soạn luật để cho người khác áp dụng, và giám sát việc thực hiện luật). Bạn sẽ biết về điều này rõ hơn khi đọc Chương 2 của phần này.\n" +
                "Phần hai: Trình bày cách tư duy pháp lý; gồm phương pháp thực hiện; các vụ án để bạn… luyện chưởng và biết tính chất của các câu hỏi pháp lý.\n" +
                "Phần ba: Đưa ra một số vụ án để các bạn tập làm một mình hầu kiểm tra mức độ sử dụng tư duy pháp lý\n" +
                "Phần bốn: Một số bài đọc thêm để bạn mở rộng kiến thức.', 'Nguyễn Ngọc Bích', 18, 1), " +
                "(5, 'Luật Pháp - Khái Lược Những Tư Tưởng Lớn (Bìa Cứng)','product8.jpg', 'Trong cuộc đời, mỗi chúng ta dù ít dù nhiều đều đã từng trải qua những thời khắc khó khăn, đau khổ. Đặc biệt đối với những bạn rời xa vòng tay che chở, bao bọc của cha mẹ và nhà trường, bước chân vào xã hội, bạn sẽ gặp phải rất nhiều trở ngại và nhận ra xã hội ngày vốn không hề đơn giản như bạn tưởng tượng.', 'Liêu Trí Phong', 8, 1)" ;
        db.execSQL(insertBook);


        // MEMBER MEMBER
        String createMember =
                "CREATE TABLE MEMBER(" +
                        "memberID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "fullname TEXT NOT NULL," +
                        " phoneNumber TEXT NOT NULL," +
                        " address TEXT NOT NULL," +
                        " password TEXT NOT NULL," +
                        " role INTEGER NOT NULL)";
        db.execSQL(createMember);

        String insertMember = "INSERT INTO MEMBER (fullname, phoneNumber, address, password, role) VALUES" +
                "('Tấn Duy', '123456789', 'quận 7, HCM', '12345678', 1)," +
                "('Tấn Bảo', '987654321', 'Gò Vấp, HCM', '12345678', 0)," +
                "('Chí Thành', '555666777', 'quận Bình Thạnh, HCM', '12345678', 0)";
        db.execSQL(insertMember);




        // RECEIPT RECEIPT
        String createReceipt =
                "CREATE TABLE RECEIPT(" +
                        "receiptID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " startDay TEXT NOT NULL," +
                        " endDay TEXT NOT NULL," +
                        " note TEXT," +
                        " memberID INTEGER NOT NULL," +
                        " FOREIGN KEY (memberID) REFERENCES MEMBER(memberID))";
        db.execSQL(createReceipt);

        String insertReceipt = "INSERT INTO RECEIPT (startDay, endDay, note, memberID) VALUES" +
                "('2024-05-01', '2024-05-10', 'Giới thiệu sách cho bạn khác', 1)," +
                "('2024-06-15', '2024-06-25', 'Cân nhắc mua luôn', 2)," +
                "('2024-07-20', '2024-07-30', '', 3)";
        db.execSQL(insertReceipt);



        // RECEIPT DETAIL      RECEIPT DETAIL
        String createReceiptDetail =
                "CREATE TABLE RECEIPTDETAIL(" +
                        "receiptID INTEGER NOT NULL," +
                        " bookID INTEGER NOT NULL," +
                        " status INTEGER NOT NULL," + // 0 có sẵn, 1 đang mượn, 2 bị mất

                        " quantity INTEGER NOT NULL," +
                        " PRIMARY KEY (receiptID, bookID)," +
                        " FOREIGN KEY (receiptID) REFERENCES RECEIPT(receiptID)," +
                        " FOREIGN KEY (bookID) REFERENCES BOOK(bookID))";
        db.execSQL(createReceiptDetail);

        String insertReceiptDetail = "INSERT INTO RECEIPTDETAIL (receiptID, bookID, status, quantity) VALUES" +
                "(1, 1, 1, 1)," +
                "(1, 2, 1, 1)," +
                "(2, 3, 0, 1)," +
                "(3, 1, 1, 2)";
        db.execSQL(insertReceiptDetail);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS CATEGORY");
            db.execSQL("DROP TABLE IF EXISTS BOOK");
            db.execSQL("DROP TABLE IF EXISTS MEMBER");
            db.execSQL("DROP TABLE IF EXISTS RECEIPT");
            db.execSQL("DROP TABLE IF EXISTS RECEIPTDETAIL");

            onCreate(db);
        }
    }
}
