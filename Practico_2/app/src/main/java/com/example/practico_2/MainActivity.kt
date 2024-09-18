package com.example.practico_2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practico_2.adapter.MyComidaAdapter
import com.example.practico_2.listener.IDcomidaLoadListener
import com.example.practico_2.model.ComidaModel
import com.example.practico_2.utils.SpaceItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.widget.RelativeLayout

class MainActivity : AppCompatActivity(), IDcomidaLoadListener {

    lateinit var comidaLoadListener: IDcomidaLoadListener
    lateinit var recyclerComida: RecyclerView
    lateinit var mainLayout: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        mainLayout = findViewById(R.id.mainLayout)
        recyclerComida = findViewById(R.id.recycler_comida)

        init()
        loadComidaFromFirebase()
    }

    private fun init() {
        comidaLoadListener = this

        val gridLayoutManager = GridLayoutManager(this, 2)
        recyclerComida.layoutManager = gridLayoutManager
        recyclerComida.addItemDecoration(SpaceItemDecoration())
    }

    private fun loadComidaFromFirebase() {
        val comidaModels: MutableList<ComidaModel> = ArrayList()
        FirebaseDatabase.getInstance()
            .getReference("Comida")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (comidaSnapshot in snapshot.children) {
                            val comidaModel = comidaSnapshot.getValue(ComidaModel::class.java)
                            comidaModel!!.key = comidaSnapshot.key
                            comidaModels.add(comidaModel)
                        }
                        comidaLoadListener.onComidaLoadSuccess(comidaModels)
                    } else {
                        comidaLoadListener.onComidaLoadFailed("No existe en el menú")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    comidaLoadListener.onComidaLoadFailed(error.message)
                }
            })
    }

    override fun onComidaLoadSuccess(comidaModelList: List<ComidaModel>?) {
        val adapter = MyComidaAdapter(this, comidaModelList!!)
        recyclerComida.adapter = adapter
    }

    override fun onComidaLoadFailed(message: String?) {
        Snackbar.make(mainLayout, message ?: "Error desconocido", Snackbar.LENGTH_LONG).show()
    }
}
