package space.dlsunity.arctour.domain.usecases.bd

import com.digitallabstudio.sandboxes.data.room.data.Bd_data
import com.digitallabstudio.sandboxes.data.room.repositories.BdDataRepository

class GetBdDataUseCase(
    private val bdDataRepository: BdDataRepository
) {
    suspend operator fun invoke(): List<Bd_data> {
        return bdDataRepository.getAllBdEntities()
    }
}