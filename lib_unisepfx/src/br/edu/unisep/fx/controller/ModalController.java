package br.edu.unisep.fx.controller;

import br.edu.unisep.fx.events.OnModalClose;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public abstract class ModalController extends AppController{

	protected AppController parentController;
	protected Stage window;
	protected OnModalClose onClose;
	protected Object[] params;
	
	protected final void onInit() {
	}

	protected abstract void onModalInit();
	
	public AppController getParentController() {
		return parentController;
	}
	
	public Stage getWindow() {
		return window;
	}
	
	public void closeModal() {
		window.close();

		if (onClose != null) {
			onClose.execute();
		}
	}

	public void setTitle(String title) {
		this.window.setTitle(title);
	}

	protected void setParams(Object... p) {
		this.params = p;
	}

	public void close(ActionEvent event) {
		closeModal();
	}
}
