package com.example.gitspy.database

import androidx.room.TypeConverter
import com.example.gitspy.models.License
import com.example.gitspy.models.Owner

class Conveters {

    @TypeConverter
    fun licenseToString(license: License) : String{
        return license.name
    }

    @TypeConverter
    fun stringToLicense(name : String) : License{
        return License(name ,name ,name ,name ,name)
    }

    @TypeConverter
    fun ownerToInt(owner: Owner) : Int{
        return owner.id
    }

    @TypeConverter
    fun intToOwner(id :Int) : Owner{
        val ids = id.toString()
        return Owner(ids , ids, ids,ids,ids,ids,ids,id,ids,ids,ids,ids,ids,true,ids,ids,ids,ids)
    }

    @TypeConverter
    fun anyToInt(any : Any): Int{
        return any.hashCode()
    }

    @TypeConverter
    fun intToAny(x : Int): Any{
        return x
    }




}