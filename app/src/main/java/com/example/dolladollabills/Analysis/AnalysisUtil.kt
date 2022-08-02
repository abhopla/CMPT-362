package com.example.dolladollabills.Analysis

object AnalysisUtil {
    fun intializaMonth(index: Int): Array<String>{
        var arrayMonth : Array<String> = arrayOf("Jan")
        when (index){
            0 -> arrayMonth =  arrayOf("Jan")
            1 -> arrayMonth = arrayOf("Jan","Feb")
            2 -> arrayMonth = arrayOf("Jan","Feb","Mar")
            3 -> arrayMonth = arrayOf("Jan","Feb","Mar","Apr")
            4 -> arrayMonth = arrayOf("Jan","Feb","Mar","Apr","May")
            5 -> arrayMonth = arrayOf("Jan","Feb","Mar","Apr","May","Jun")
            6 -> arrayMonth = arrayOf("Jan","Feb","Mar","Apr","May","Jun","Jul")
            7 -> arrayMonth = arrayOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug")
            8 -> arrayMonth = arrayOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep")
            9 -> arrayMonth = arrayOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct")
            10 -> arrayMonth = arrayOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov")
            11 -> arrayMonth = arrayOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")
        }
        return arrayMonth
    }

    fun intializaLongList(index: Int): Array<Long>{
        var arrayDouble : Array <Long> = arrayOf(0L)
        when (index){
            0 -> arrayDouble = arrayOf(0L)
            1 -> arrayDouble = arrayOf(0L,0L)
            2 -> arrayDouble = arrayOf(0L,0L,0L)
            3 -> arrayDouble = arrayOf(0L,0L,0L,0L)
            4 -> arrayDouble = arrayOf(0L,0L,0L,0L,0L)
            5 -> arrayDouble = arrayOf(0L,0L,0L,0L,0L,0L)
            6 -> arrayDouble = arrayOf(0L,0L,0L,0L,0L,0L,0L)
            7 -> arrayDouble = arrayOf(0L,0L,0L,0L,0L,0L,0L,0L)
            8 -> arrayDouble = arrayOf(0L,0L,0L,0L,0L,0L,0L,0L,0L)
            9 -> arrayDouble = arrayOf(0L,0L,0L,0L,0L,0L,0L,0L,0L,0L)
            10 -> arrayDouble = arrayOf(0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L)
            11 -> arrayDouble = arrayOf(0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L)
        }
        return arrayDouble
    }

    fun intializaLongList_Any(index: Int): Array<Any>{
        var arrayDouble : Array<Any> = arrayOf(0)
        when (index){
            0 -> arrayDouble = arrayOf(0)
            1 -> arrayDouble = arrayOf(0L,0L)
            2 -> arrayDouble = arrayOf(0L,0L,0L)
            3 -> arrayDouble = arrayOf(0L,0L,0L,0L)
            4 -> arrayDouble = arrayOf(0L,0L,0L,0L,0L)
            5 -> arrayDouble = arrayOf(0L,0L,0L,0L,0L,0L)
            6 -> arrayDouble = arrayOf(0L,0L,0L,0L,0L,0L,0L)
            7 -> arrayDouble = arrayOf(0L,0L,0L,0L,0L,0L,0L,0L)
            8 -> arrayDouble = arrayOf(0L,0L,0L,0L,0L,0L,0L,0L,0L)
            9 -> arrayDouble = arrayOf(0L,0L,0L,0L,0L,0L,0L,0L,0L,0L)
            10 -> arrayDouble = arrayOf(0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L)
            11 -> arrayDouble = arrayOf(0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L)
        }
        return arrayDouble
    }

    //todo: if number is larger than 11?
    fun intializaSpendingGraphData(index: Int): Array<Any>{
        var graphData : Array<Any> = arrayOf(arrayOf("no data",0))
        when (index){
            0 -> graphData = arrayOf(arrayOf("no data",0))
            1 -> graphData = arrayOf(arrayOf("aa",1))
            2 -> graphData = arrayOf(arrayOf("aa",1),arrayOf("aa",1))
            3 -> graphData = arrayOf(arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1))
            4 -> graphData = arrayOf(arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1))
            5 -> graphData = arrayOf(arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1))
            6 -> graphData = arrayOf(arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1))
            7 -> graphData = arrayOf(arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1))
            8 -> graphData = arrayOf(arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1))
            9 -> graphData = arrayOf(arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1))
            10 -> graphData = arrayOf(arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1))
            11 -> graphData = arrayOf(arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1),arrayOf("aa",1))
        }
        return graphData
    }



}