/**
 *
 */
package io.github.jsoagger.jfxcore.engine.events;

import org.kordamp.ikonli.javafx.FontIcon;

import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.RootStructureContentController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * @author vonji
 *
 */
public class RSBeginProcessing extends GenericEvent{

	private Node contentNode;
	private Label defaultProcessingLabel = new Label();
	private AbstractViewController controller;
	private SimpleBooleanProperty processing = new SimpleBooleanProperty(false);

	public RSBeginProcessing() {
	}

	public RSBeginProcessing(AbstractViewController controller, boolean buildDefault) {
		this.controller = controller;
		if(buildDefault) {
			setDefault();
		}
	}

	public SimpleBooleanProperty processingProperty() {
		return processing;
	}

	public void launchCloseTimeline(Duration duration) {
		Timeline timeline = new Timeline();
		KeyFrame keyFrame = new KeyFrame(duration);
		timeline.getKeyFrames().add(keyFrame);
		timeline.setOnFinished(e -> {
			controller.dispatchEvent(new RSEndProcessing());
		});
		timeline.playFromStart();
	}

	private void setDefault() {
		defaultProcessingLabel.setText("Processing...");
		defaultProcessingLabel.getStyleClass().add("ep-root-structure-processing-pane-label");
		contentNode =  defaultProcessingLabel;
	}

	public void setSuccessMessage(String message, boolean close) {
		if(Platform.isFxApplicationThread()) {
			_doSetSuccessMessage(message, close);
		}
		else {
			Platform.runLater(()-> {
				_doSetSuccessMessage(message, close);
			});
		}
	}

	public void setSuccessAction(boolean launchClose) {
		if(Platform.isFxApplicationThread()) {
			_doSetSuccessAction(launchClose, null);
		}
		else {
			Platform.runLater(()-> {
				_doSetSuccessAction(launchClose, null);
			});
		}
	}

	public void setSuccessAction(boolean launchClose, String successIcon) {
		if(Platform.isFxApplicationThread()) {
			_doSetSuccessAction(launchClose, successIcon);
		}
		else {
			Platform.runLater(()-> {
				_doSetSuccessAction(launchClose, successIcon);
			});
		}
	}

	public void setErrorAction(boolean launchClose) {
		if(Platform.isFxApplicationThread()) {
			_doSetErrorAction(launchClose);
		}
		else {
			Platform.runLater(()-> {
				_doSetErrorAction(launchClose);
			});
		}
	}

	private void _doSetErrorAction(boolean launchClose) {
		FontIcon icon = new FontIcon("fa-exclamation-triangle:32");
		 defaultProcessingLabel.setGraphic(icon);
		 defaultProcessingLabel.setText(" Error");
		 icon.getStyleClass().add("red-ikonli");

		 if(launchClose)
			 launchCloseTimeline(Duration.millis(1000));
	}

	private void _doSetSuccessAction(boolean launchClose, String successIcon) {
		 FontIcon icon = new FontIcon(successIcon != null ? successIcon : "fa-check-circle:32");
		 defaultProcessingLabel.setGraphic(icon);
		 defaultProcessingLabel.setText("");
		 icon.getStyleClass().add("green-ikonli");

		 if(launchClose)
			 launchCloseTimeline(Duration.millis(800));
	}

	private void _doSetSuccessMessage(String message, boolean close) {
		 FontIcon icon = new FontIcon("fa-check:16");
		 defaultProcessingLabel.setGraphic(icon);
		 icon.getStyleClass().add("green-ikonli");
		 defaultProcessingLabel.setText(message);
		 defaultProcessingLabel.pseudoClassStateChanged(PseudoClass.getPseudoClass("success"), true);

		 if(close) {
			 launchCloseTimeline(Duration.millis(800));
		 }
	}


	public RSBeginProcessing(Node contentNode) {
		this.contentNode = contentNode;
	}

	@Override
	public Class getFilter() {
		return RSBeginProcessing.class;
	}

	/**
	 * @return the contentNode
	 */
	public Node getContentNode() {
		return contentNode;
	}

	/**
	 * @param contentNode the contentNode to set
	 */
	public void setContentNode(Node contentNode) {
		this.contentNode = contentNode;
	}

	/**
	 * Dispatch the event
	 */
	public void dispatch() {
		// Try to dispatch this event directly to RootStructureContentController if there is one
		// to avoid thread async processing so, the pane is displayed after processing
		if(controller != null) {
			RootStructureContentController c = ((AbstractViewController)controller).getRootStructure().getRootStructureContent();
			if(c != null) {
				c.handle(this);
			}
			else {
				((AbstractViewController)controller).dispatchEvent(this);
			}
		}
	}
}
