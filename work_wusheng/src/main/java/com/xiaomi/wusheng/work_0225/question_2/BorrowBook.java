package com.xiaomi.wusheng.work_0225.question_2;

import java.sql.*;

public class BorrowBook{
    public static void borrowBook(String title, int userId){
        Connection conn = null;
        PreparedStatement update = null;
        PreparedStatement insert = null;

        try{
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            String select = "SELECT book_id, stock FROM books WHERE title = ?";
            PreparedStatement ps = conn.prepareStatement(select);
            ps.setString(1, title);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                int bookId = resultSet.getInt("book_id");
                int stock = resultSet.getInt("stock");
                if(stock > 0){
                    String updateQuery = "UPDATE books SET stock = stock - 1 WHERE book_id = ?";
                    update = conn.prepareStatement(updateQuery);
                    update.setInt(1, bookId);
                    int updated = update.executeUpdate();
                    if(updated == 0){
                        throw new SQLException("更新库存失败");
                    }

                    String insertQuery = "INSERT INTO borrow_records (book_id, user_id, borrow_date) VALUES (?, ?, ?)";
                    insert = conn.prepareStatement(insertQuery);
                    insert.setInt(1, bookId);
                    insert.setInt(2, userId);
                    insert.setDate(3, new Date(System.currentTimeMillis()));
                    int inserted = insert.executeUpdate();
                    if(inserted == 0){
                        throw new SQLException("插入借阅记录失败");
                    }

                    conn.commit();
                    System.out.println("借书成功！");
                }else{
                    System.out.println("库存不足，无法借阅！");
                }
            }else{
                System.out.println("书籍不存在！");
            }
        }catch(SQLException e){
            try{
                conn.rollback();
            }catch(SQLException rollbackException){
                rollbackException.printStackTrace();
            }
        }finally{
            try{
                if(update != null){
                    update.close();
                }
                if(insert != null){
                    insert.close();
                }
                if(conn != null){
                    conn.close();
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}

