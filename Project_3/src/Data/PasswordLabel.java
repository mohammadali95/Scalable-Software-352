package Data;

public class PasswordLabel {
	private String label;
	private String password;
	private boolean isHidden;

	public PasswordLabel(String label, String password, boolean isHidden) {
		this.label = label;
		this.password = password;
		this.isHidden = isHidden;
	}

	public String getLabel() {
		return label;
	}

	public String getPassword() {
		if (isHidden) {
			return "********";
		} else {
			return password;
		}
	}

	public void setPassword(String password) {
		this.password = password;
		isHidden = false;

	}

	public boolean isHidden() {
		return isHidden;
	}

	public void toggleHidden(){
		isHidden = !isHidden;
	}
}
