package Enum;

public enum ErrorCode {
	NoLogin{
		@Override
		public String toString(){
			return "{ \"error\":\"100 You Should Login\" ," +
					"  \"code\":\"100\" , \"msg\":\"You Should Login\"}";
		}
	},

	POST{
		@Override
		public String toString(){
			return "POST";
		}
	},

	PUT{
		@Override
		public String toString(){
			return "PUT";
		}
	},

	DELETE{
		@Override
		public String toString(){
			return "DELETE";
		}
	},
	//GET POST PUT DELETE
}
