package com.example.gitspy.database

//class Conveters {
//
//    @TypeConverter
//    fun licenseToString(license: License) : String{
//        return license.name
//    }
//
//    @TypeConverter
//    fun stringToLicense(name : String) : License{
//        return License(name ,name ,name ,name ,name)
//    }
//
//    @TypeConverter
//    fun ownerToString(owner: Owner) : String{
//        val gson =Gson()
//        return gson.toJson(owner)
//    }
//
//    @TypeConverter
//    fun stringToOwner(str :String) : Owner?{
//        val gson = Gson()
//        if (TextUtils.isEmpty(str)) return null
//        return gson.fromJson(str,Owner::class.java)
//    }
//
//}