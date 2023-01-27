package space.dlsunity.arctour.domain.usecases.bd

import com.digitallabstudio.sandboxes.data.room.data.Bd_data
import com.digitallabstudio.sandboxes.data.room.repositories.BdDataRepository

class DeleteBdDataUseCase (
    private val bdDataRepository: BdDataRepository
) {
    suspend operator fun invoke(bdData: Bd_data){
        return bdDataRepository.deleteBdEntity(bdData)
    }
}