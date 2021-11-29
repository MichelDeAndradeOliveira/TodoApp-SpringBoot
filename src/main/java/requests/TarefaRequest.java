package requests;

public class TarefaRequest {
	private String content;
	private Boolean completed = Boolean.FALSE;
	
	
	public TarefaRequest(String content, Boolean completed) {
		this.content = content;
		this.completed = completed;
	}
	
	public TarefaRequest() {
		
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Boolean getCompleted() {
		return completed;
	}
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	
}
