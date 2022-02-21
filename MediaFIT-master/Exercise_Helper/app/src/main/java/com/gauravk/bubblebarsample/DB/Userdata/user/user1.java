package com.gauravk.bubblebarsample.DB.Userdata.user;

import com.google.gson.annotations.SerializedName;

public class user1 {
        private String score;

        private String sex;

        private String profile;

        private String name;

        private String email;

        private String age;

        public String getScore ()
        {
            return score;
        }

        public void setScore (String score)
        {
            this.score = score;
        }

        public String getSex ()
        {
            return sex;
        }

        public void setSex (String sex)
        {
            this.sex = sex;
        }

        public String getProfile ()
        {
            return profile;
        }

        public void setProfile (String profile)
        {
            this.profile = profile;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public String getEmail ()
        {
            return email;
        }

        public void setEmail (String email)
        {
            this.email = email;
        }

        public String getAge ()
        {
            return age;
        }

        public void setAge (String age)
        {
            this.age = age;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [score = "+score+", sex = "+sex+", profile = "+profile+", name = "+name+", email = "+email+", age = "+age+"]";
        }
}
