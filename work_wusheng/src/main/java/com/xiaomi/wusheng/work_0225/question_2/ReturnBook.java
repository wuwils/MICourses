package com.xiaomi.wusheng.work_0225.question_2;

import java.sql.*;

public class ReturnBook{
    public static void returnBook(String title, int userId){
        Connection conn = null;
        PreparedStatement update = null;
        PreparedStatement delete = null;

        try{
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            String selectBook = "SELECT book_id FROM books WHERE title = ?";
            PreparedStatement ps = conn.prepareStatement(selectBook);
            ps.setString(1, title);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                int bookId = resultSet.getInt("book_id");
                String selectRecordQuery = "SELECT record_id FROM borrow_records WHERE book_id = ? AND user_id = ?";
                PreparedStatement selectRecord = conn.prepareStatement(selectRecordQuery);
                selectRecord.setInt(1, bookId);
                selectRecord.setInt(2, userId);
                ResultSet recordRs = selectRecord.executeQuery();
                if(recordRs.next()){
                    int recordId = recordRs.getInt("record_id");
                    String updateQuery = "UPDATE books SET stock = stock + 1 WHERE book_id = ?";
                    update = conn.prepareStatement(updateQuery);
                    update.setInt(1, bookId);
                    update.executeUpdate();

                    String deleteQuery = "DELETE FROM borrow_records WHERE record_id = ?";
                    delete = conn.prepareStatement(deleteQuery);
                    delete.setInt(1, recordId);
                    delete.executeUpdate();

                    conn.commit();
                    System.out.println("还书成功！");
                }else{
                    System.out.println("没有找到该用户的借阅记录！");
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
                if(delete != null){
                    delete.close();
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

