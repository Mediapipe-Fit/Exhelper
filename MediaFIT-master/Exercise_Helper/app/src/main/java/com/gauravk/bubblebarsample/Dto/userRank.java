package com.gauravk.bubblebarsample.Dto;

import com.google.gson.annotations.SerializedName;

public class userRank {
        private int score;

        private String profile;

        private String name;

        private int ranking;


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

        public int getRanking () { return ranking; }

        public void setRanking (int ranking) { this.ranking = ranking; }


        @Override
        public String toString()
        {
            return "ClassPojo [score = "+score+", profile = "+profile+", name = "+name+", ranking = "+ranking+"]";
        }
}
