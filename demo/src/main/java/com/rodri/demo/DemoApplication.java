package com.rodri.demo;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import com.rodri.demo.controlador.ArchivoBinarioGestor;
import com.rodri.demo.controlador.EquipoGestor;
import com.rodri.demo.controlador.UsuarioGestor;
import com.rodri.demo.db.ArchivoDb;
import com.rodri.demo.db.EquipoDb;
import com.rodri.demo.db.UsuarioDb;
import com.rodri.demo.entidad.ArchivoBinario;
import com.rodri.demo.entidad.Equipo;
import com.rodri.demo.entidad.Usuario;

@SpringBootApplication
public class DemoApplication {
	private static final String CARPETA_ARCHIVOS = "archivos/";

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		// 1. Cargar usuarios existentes de MySQL al ArrayList
		UsuarioDb.cargarUsuarios();
		EquipoDb.cargarEquipos();

		// 2. Crear nuevo usuario (se guarda en ArrayList y MySQL)
		Usuario nuevoUsuario = new Usuario("Marias Garciasss", "mariasss@example.com",
				"123");
		UsuarioGestor.crearUsuario(nuevoUsuario);
		UsuarioDb.guardarUsuario(nuevoUsuario);
		System.out.println("Nuevo usuario -------" + nuevoUsuario);

		// // 3. Crear nuevo Equipo
		// // 1. CREATE - Crear nuevo equipo
		System.out.println("\n--- CREAR NUEVO EQUIPO ---");
		Equipo nuevoEquipo = new Equipo("iPhone Pro Max 16", "Nueva tecnología");
		EquipoGestor.crearEquipo(nuevoEquipo);
		EquipoDb.guardarEquipo(nuevoEquipo);
		System.out.println("Equipo creado: " + nuevoEquipo);

		// 2. READ - Leer/Buscar equipos
		System.out.println("\n--- BUSCAR EQUIPOS ---");
		// Buscar por ID (usando el ID del equipo recién creado)
		Equipo equipoEncontrado = EquipoGestor.buscarPorId(nuevoEquipo.getId());
		if (equipoEncontrado != null) {
			System.out.println("Equipo encontrado: " + equipoEncontrado);
		} else {
			System.out.println("Equipo no encontrado");
		}

		// 3. UPDATE - Actualizar equipo
		System.out.println("\n--- ACTUALIZAR EQUIPO ---");
		if (equipoEncontrado != null) {
			equipoEncontrado.setNombre("iPhone Pro Max 16 (2024)");
			equipoEncontrado.setDescripcion("Última tecnología 2024");
			EquipoDb.actualizarEquipo(equipoEncontrado);
			System.out.println("Equipo actualizado: " + equipoEncontrado);
		}

		// 4. DELETE - Eliminar equipo
		System.out.println("\n--- ELIMINAR EQUIPO ---");
		if (equipoEncontrado != null) {
			int idAEliminar = equipoEncontrado.getId();
			EquipoGestor.eliminarEquipo(idAEliminar);
			EquipoDb.eliminarEquipo(idAEliminar);
			System.out.println("Equipo con ID " + idAEliminar + " eliminado");

			// Verificar que se eliminó
			Equipo equipoEliminado = EquipoGestor.buscarPorId(idAEliminar);
			if (equipoEliminado == null) {
				System.out.println("Confirmación: El equipo ya no existe");
			}
		}

		// 4. Crear nuevo archivo
		try {
			String nombreArchivo = "Minuta.jpg";
			String tipoArchivo = "image/jpg";

			// Carga el archivo desde el classpath
			ClassPathResource resource = new ClassPathResource(CARPETA_ARCHIVOS + "imagenes/" + nombreArchivo);
			byte[] contenido = resource.getInputStream().readAllBytes();

			ArchivoBinario archivo = new ArchivoBinario(nombreArchivo, tipoArchivo, contenido);
			ArchivoDb.guardarArchivoBinario(archivo);
			ArchivoBinarioGestor.crearArchivo(archivo);

			System.out.println("Archivo guardado exitosamente con ID: " + archivo.getId());
		} catch (IOException e) {
			System.err.println("Error al procesar el archivo:");
			e.printStackTrace();
		}
	}
}
