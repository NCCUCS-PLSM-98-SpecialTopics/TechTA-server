package Enum;

public enum RMethod {
	GET{
		@Override
		public String toString(){
			return "GET";
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
