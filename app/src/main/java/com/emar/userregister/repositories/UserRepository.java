package com.emar.userregister.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.emar.userregister.dao.UserDao;
import com.emar.userregister.database.AppDatabase;
import com.emar.userregister.entities.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class UserRepository {

   private UserDao userDao;

   public UserRepository(Application application){

      userDao = AppDatabase.getInstance(application).userDao();
   }

   public long insertUser(User user){

      try {
       return   new InsertUserAsyncTask(userDao).execute(user).get();
      } catch (InterruptedException e) {
         e.printStackTrace();
      } catch (ExecutionException e) {
         e.printStackTrace();
      }

      return -1;
   }

   public LiveData<List<User>> getUsersAll(){
      return userDao.getUsersAll();
   }


   //**** Async Task ******//

   private static class InsertUserAsyncTask extends AsyncTask<User, Void, Long>{

      private UserDao userDao;

      private InsertUserAsyncTask(UserDao userDao){
         this.userDao = userDao;
      }
      @Override
      protected Long doInBackground(User... users) {
         return userDao.insert(users[0]);
      }
   }
}
