package phs.com.circuitouniversitario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Request {

    @GET("/eventos")
    Call<List<Evento>> getEventos();

    @POST("/eventos")
    Call<Void> postEvento(@Body Evento evento);

    @DELETE("/eventos/{id}")
    Call<Evento> deleteEvento(@Path("id") Integer eventoId);
}
