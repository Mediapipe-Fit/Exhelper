package com.gauravk.bubblebarsample.DB.Userdata.user;

import com.google.gson.annotations.SerializedName;

public class user1 {
        private int score;

        private String profile;

        private String name;

        private int ranikng;


        public int getScore ()
        {
            return score;
        }

        public void setScore (int score)
        {
            this.score = score;
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

        public int getRanikng () { return ranikng; }

        public void setRanikng (int ranikng) { this.ranikng = ranikng; }


        @Override
        public String toString()
        {
            return "ClassPojo [score = "+score+", profile = "+profile+", name = "+name+", ranking = "+ranikng+"]";
        }
}
