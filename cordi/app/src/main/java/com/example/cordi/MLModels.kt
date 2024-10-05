package com.example.cordi

import ai.djl.ndarray.NDList
import ai.djl.ndarray.NDManager
import ai.djl.tensorflow.engine.TfModel
import ai.djl.tensorflow.model.TensorFlowModel
import java.nio.file.Paths

object MLModels {

    private lateinit var model: TensorFlowModel

    fun loadModel() {
        model = TensorFlowModel.newInstance(Paths.get("assets/models/trained_model.pkl"))
    }

    fun predict(inputData: FloatArray): Float {
        val manager = NDManager.newBaseManager()
        val inputNDArray = manager.create(NDList(inputData))

        val result = model.predict(inputNDArray)
        return result.singletonOrThrow().getFloat()
    }
}
