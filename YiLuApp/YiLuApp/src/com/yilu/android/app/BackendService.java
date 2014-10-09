package com.yilu.android.app;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import android.app.Application; 
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.avos.avoscloud.*;

interface ImageListCallBack { public void onImageListUpdated();}
interface UserLogInCallback { 
	public void LogInSuccess();
	public void LogInFail();
}
    
public class BackendService {

    public BackendService(Application app){
        AVOSCloud.initialize(app, "i6xp9je0wdny22t0k5m13564nh1cloby9oih6xg29s9tpy96", 
        		"pbfveozcasgmn37uw4yne2k6892dio4z32g2o8wb6y26lcb9");

	    createDefaultFolder();
	    dataList = new ArrayList<Data>();
	    Log.v("初始化", "avos clould init");
	}
    private AVUser user;

    private ImageListCallBack callback;
	public ArrayList<Data> dataList;
	public final String FILE_DIR = new String(Environment.getExternalStorageDirectory().getPath() + 
			File.separator +"com.yilu.andriod.app" + File.separator);
    private void createDefaultFolder()
    {
    	File folder = new File(FILE_DIR);
        if (!folder.exists())
        {
            if (folder.mkdir())
            {
                Log.w("File", "Deault Folder creation failure: " + FILE_DIR);
            }
        }
        return;
    }
    
    @AVClassName("Image")
    public class Image extends AVObject{
        public Image() {
            super("Image");
        }
     
        public AVUser getPublisher() {
            return (AVUser)super.getAVUser("publisher");
        }
        public void setPublisher(AVUser user) {
            super.put("publisher", user);
        }
     
        public String getCaption() {
            return getString("caption");
        }
        public void setCaption(String caption) {
            put("caption", caption);
        }
        public String getTakenAt() {
            return super.getCreatedAt().toString();
        }
     
        private void setUrl(String url)
        {
        	put("fileUrl", url);
        }
        
        public String getUrl()
        {
        	return getString("fileUrl");
        }
        
        private void setHeight(int height)
        {
        	put("height", height); 
        }
        
        private void setWidth(int width)
        {
        	put("width", width);
        }
        
        private void increaseNbLikes(final int nb)
        {
        	put("nbLikes", nb + 1);
        }
        
        public AVFile getRawImage() {
            return super.getAVFile("imageFile");
        }
        public void setRawImage(AVFile file) {
            super.put("imageFile", file);
        }
     
        @SuppressWarnings("unchecked")
        public List<Comment> getComments() {
            return (List<Comment>)getList("comments");
        }
        public void addComment(Comment com) {
            addUnique("comments", com);
        }
        public AVRelation<AVUser> getLiker() {
            AVRelation<AVUser> relation = getRelation("likes");
            return relation;
        }
        public void removeLiker(AVUser user) {
            AVRelation<AVUser> users = getLiker();
            users.remove(user);
            this.saveInBackground();
        }
        public void addLiker(AVUser user) {
            AVRelation<AVUser> users = getLiker();
            users.add(user);
            this.saveInBackground();
        }
        
        List<AVUser> likedUsers = new ArrayList<AVUser>();
        public void setLikedUsers(List<AVUser> usr) {
            if (null == usr) return;
            this.likedUsers = usr;
        }
        public List<AVUser> getLikedUsers() {
            return this.likedUsers;
        }
        public int getLikerCount() {
            return this.likedUsers.size();
        }
    }

    @AVClassName("Comment")
    public class Comment extends AVObject{
        public Comment() {
            super();
        }
        public String getContent() {
            return getString("content");
        }
        public void setContent(String value) {
            put("content", value);
        }
        public void setCreator(AVUser user) {
            put("creator", user);
        }
        public AVUser getCreator() {
            return getAVUser("creator");
        }
    }
    
    public void userLogOut(){
    	if(isUserLogIn())
    		user.logOut();
    }

    public boolean isUserLogIn(){
    	user =  user.getCurrentUser();
    	if(null == user) return false;
    	else return true;
    }
    
//    public List<AVObject> ImgFileLists;
    
	public void UserLogIn(String userName, String password, final UserLogInCallback logInCb){
		AVUser user = new AVUser();
		user.setUsername(userName);
		user.setPassword(password);
		
		user.logInInBackground(userName, password, new LogInCallback<AVUser>() {
		    public void done(AVUser user, AVException e) {
		        if (user != null) {
		            logInCb.LogInSuccess();
		        } else {
		            logInCb.LogInFail();
		        }
		    }
		});
	}
	

	public void UserSignUp(String userName, String password, String eMail, String phone){
		AVUser user = new AVUser();
		user.setUsername(userName);
		user.setPassword(password);
		user.setEmail(eMail);

		// 其他属性可以像其他AVObject对象一样使用put方法添加
		user.put("phone", phone);

		user.signUpInBackground(new SignUpCallback() {
		    public void done(AVException e) {
		        if (e == null) {
		            // successfully
		        } else {
		            // failed
		        }
		    }
		});
	}
	
	public void ImgUpload (final String txtCaption, final URI processedImageUri)
	{
		String username = AVUser.getCurrentUser().getUsername();
		
		Log.d("lzw", "my"+ username); 
		long timeInSeconds = (System.currentTimeMillis()/1000);
		try {
			final Bitmap bm = BitmapFactory.decodeFile(processedImageUri.getPath());
			final int height = bm.getHeight();
			final int width = bm.getWidth();
			System.out.println("size: "+height + "  "+ width + "  " + height*width*4.0/1000.0);
			bm.recycle();
			final AVFile remoteFile = AVFile.withFile(username + timeInSeconds, new File(processedImageUri.getPath()));
		    remoteFile.saveInBackground(new SaveCallback() {
	            @Override
	            public void done(AVException e) {
	                if (e == null) {
	                    String fileUrl = remoteFile.getUrl();
	        		    Image image = new Image();
	        		    image.setPublisher(AVUser.getCurrentUser());
	        		    image.setRawImage(remoteFile);
	        		    image.setCaption(txtCaption);
	        		    image.setUrl(fileUrl);
	        		    image.setHeight(height);
	        		    image.setWidth(width);
	        		    image.saveInBackground();
	                }
	                Log.d("AVFile", "file uploading failure with excpetion");
	            }
	        });
//		    Image image = new Image();
//		    image.setPublisher(AVUser.getCurrentUser());
//		    image.setRawImage(remoteFile);
//		    image.setCaption(txtCaption);
//		    image.saveInBackground();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException IOe) {
			// TODO Auto-generated catch block
			IOe.printStackTrace();
		}

	}
	

	public void GetImgList(ImageListCallBack cb)
	{
		Log.d("LZW", "getImglist");
		callback = cb;
	    AVQuery<AVObject> query = new AVQuery<AVObject>("Image");
	    query.setTrace(true);
	    Log.d("LZW", "class " + query.getClassName());
	    query.orderByDescending("nbLikes");
	    query.include("publisher");
	    query.include("rawFile");
//	    query.include("comments");
//	    query.include("likes");

		Log.d("LZW", "before query");
		query.findInBackground(new FindCallback<AVObject>() {
		    public void done(List<AVObject> avObjects, AVException e) {
		        if (e == null) {
		            Log.d("成功", "查询到" + avObjects.size() + " 条符合条件的数据");
					for (AVObject tmp : avObjects) {
			        	// final String fileName = new String(FILE_DIR + tmp.getObjectId());
			        	// AVFile cloudImgFile = tmp.getAVFile("imageFile");
						Data item = new Data();
						int height = tmp.getInt("height");
						int width = tmp.getInt("width");
						item.aspectRatio = (double)height/width; 
						Log.d("LZW", "height:" + height + ", width:" + width + ", Ratio:" + item.aspectRatio);
			    		item.imageUrl = tmp.getString("fileUrl");
			    		item.title = tmp.getString("caption");
			    		item.description = "With Imag on avos Cloud";
			    		item.yilucount = tmp.getInt("nbLikes") ; 
			    		// Each of the data has to be filled, otherwise lead to NullPointer error in DataAdapter
			    		Log.d("LZW", "url = " + item.imageUrl + "; title = " + item.title);
			    		dataList.add(item);
//			        	cloudImgFile.getDataInBackground(new GetDataCallback(){
//			        		public void done(byte[] data, AVException e){
//			        			File localFile = new File(fileName);
//			        			FileOutputStream out;
//								try {
//									out = new FileOutputStream(localFile);
//				        			out.write(data);
//				        			out.close();
//								} catch (FileNotFoundException e1) {
//									// TODO Auto-generated catch block
//									e1.printStackTrace();
//								} catch (IOException e1) {
//									// TODO Auto-generated catch block
//									e1.printStackTrace();
//								}
//
//			        		}
//			        	});
			        	
			        }
					callback.onImageListUpdated();
//		    		activity.mGridView.setAdapter(mAdapter);
		        } else {
		            Log.e("失败", "查询错误: " + e.getMessage());
		        }
		    }
		});
		Log.d("LZW", "after query");


             
//	                AVRelation<AVUser> likers = img.getLiker();
//	                likers.getQuery().findInBackground(new FindCallback<AVUser>() {
//	                    public void done(List<AVUser> results, AVException e) {
//	                        if (e == null) {
//	                            // results have all the Posts the current user liked.
//	                            img.setLikedUsers(results);
//	                        }
//	                    }
//	                });
//	                List<Comment> comments = img.getComments();
//	                if (null != comments) {
//	                    for (Comment cmt: comments) {
//	                        cmt.fetchCreator();
//	                    }
//	                }
//	                instagramImageList.add(img);
	            
//	            adapter.notifyDataSetChanged();

	}
	
	public boolean AddComments(Context ImageListActivity,String comment)
	{
//	     final Comment comt = new Comment();
//	     comt.setContent(comment);
//	     comt.setCreator(AVUser.getCurrentUser());
//	     comt.saveInBackground(new SaveCallback(){
//	         public void done(com.avos.avoscloud.AVException arg0) {
//	             if (null != arg0) {
//	                 Toast.makeText(ImageListActivity.this,
//	                         "Save Comment failed", Toast.LENGTH_SHORT).show();
//	             } else {
//	                 image.addComment(comt);
//	                 image.saveInBackground(new SaveCallback() {
//	                     public void done(com.avos.avoscloud.AVException arg0) {
//	                         if (null == arg0) {
//	                             Toast.makeText(ImageListActivity.this,
//	                                     "Comment successful", Toast.LENGTH_SHORT).show();
//	                             adapter.notifyDataSetChanged();
//	                         } else {
//	                             Toast.makeText(ImageListActivity.this,
//	                                     "Save Comment2Image failed", Toast.LENGTH_SHORT).show();
//	                         }
//	                     }
//	                  });
//	             }
//	         }
//	     });
		return true;
	}
	

	
}

