package space.dlsunity.arctour.di

import com.digitallabstudio.sandboxes.data.room.repositories.BdDataRepository
import org.koin.dsl.module
import space.dlsunity.arctour.domain.usecases.bd.*

val domainModule = module {

    //Room bdData
    factory<GetBdDataByIdUseCase> {
        GetBdDataByIdUseCase(bdDataRepository = get<BdDataRepository>())
    }
    factory<GetBdDataUseCase> {
        GetBdDataUseCase(bdDataRepository = get<BdDataRepository>())
    }
    factory<SaveBdDataUseCase> {
        SaveBdDataUseCase(bdDataRepository = get<BdDataRepository>())
    }
    factory<DeleteBdDataUseCase> {
        DeleteBdDataUseCase(bdDataRepository = get<BdDataRepository>())
    }
    factory<DeleteAllBdDataUseCase> {
        DeleteAllBdDataUseCase(bdDataRepository = get<BdDataRepository>())
    }

}
