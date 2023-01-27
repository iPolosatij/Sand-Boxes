package space.dlsunity.arctour.domain.usecases.bd

import com.digitallabstudio.sandboxes.data.room.data.Bd_data
import com.digitallabstudio.sandboxes.data.room.repositories.BdDataRepository

class SaveBdDataUseCase (
    private val bdDataRepository: BdDataRepository
) {
    suspend operator fun invoke(chat: Bd_data){
        return bdDataRepository.saveBdEntity(chat)
    }
}