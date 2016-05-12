
public class ImageTypes {
	Type t;
	
	public enum Type {
		EDIT, DELETE, INSERT
	}
	public ImageTypes(){}
	public ImageTypes(Type t) {
		this.t = t;
	}

	public String getImage() {
		switch (t) {
		case EDIT:
			return "";
		case DELETE:
			return "";
		case INSERT:
			return "";
		default:
			return "";
		}
	}
}
