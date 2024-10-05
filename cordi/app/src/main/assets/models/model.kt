import ai.onnxruntime.OnnxRuntime
import ai.onnxruntime.OnnxTensor

class MyModel(context: Context) {
    private val ortEnvironment = OnnxRuntime.Environment.create()
    private val ortSession = ortEnvironment.createSession("cordi.onnx")

    fun runModel(input: FloatArray): FloatArray {
        val inputTensor = OnnxTensor.createTensor(ortEnvironment, input)
        val output = ortSession.run(Collections.singletonList(inputTensor))
        // Extract output from the result
        return output[0].value as FloatArray
    }
}
