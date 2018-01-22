# ADS04 Android

## 수업 내용

- RxJava를 활용한 retrofit을 사용 예제 학습

## Code Review

```Java
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1. 생성
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(iweather.SERVER_URL);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create()); // RxJAVA를 사용하기위한 메소드

        Retrofit retrofit = builder.build();
        //2. 서비스 만들기< 인터페이스로부터
        iweather service = retrofit.create(iweather.class);

        //3. 옵져버(Emitter) 생성
        Observable<Weather> observable = service.getData(iweather.SERVER_KEY, 1, 10, "동작");

        //4. 발행시작
        observable
                //5. 구독
                .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        weather -> {
                            String result="";
                            for (Row row : weather.getRealtimeWeatherStation().getRow()) {
                                result += "지역명"+row.getNAME()+"\n";
                                result += "온도"+row.getSAWS_TA_AVG()+"\n";
                                result += "지역명"+row.getSAWS_HD()+"\n";
                            }
                            ((TextView) findViewById(R.id.result)).setText(result);

                        }
                );

    }
}


interface iweather {

    public static final String SERVER_URL = "http://openAPI.seoul.go.kr:8088/";
    public static final String SERVER_KEY = "47516265416a697337374e7872556a";

    @GET("{key}/json/RealtimeWeatherStation/{skip}/{count}/{gu}")
    Observable<Weather> getData(
            @Path("key") String server_key,
            @Path("skip") int skip,
            @Path("count") int count,
            @Path("gu") String gu);
}
```


## 보충설명

### retrofit

> android/java용으로 개발된 오픈 소스 Request 라이브러리

> Restful api에 맞는 요청을 할 때 사용이 됨.

- 특징 

1. 해당 프로토콜과 그에 따르는 파라미터를 interface로 명시를 해줘야 하는 특징, 
2. REST 뿐만 아니라 파일업로드, 다운로드 등 모든 서버콜에 대해 짧고 간결한 코드로 대응이 가능, 
3. 성능이 향상
4. JSON파싱을 gson 파서가 알아서 해줘서 코드가 더욱 줄어듬
5. 콜백은 메인 쓰레드에서 돌고 통신부는 알아서 쓰레드에서 돌아서 별도의 asyncTask나 쓰레드를 만들필요가 없음. 코드 어디서든 호출 가능
6. Retrofit은 HTTP API를 자바 인터페이스 형태로 사용가능 - 안드로이드에서 사용가능.


※ TODO- library 관련 repo 따로 만들어서 정리할 예정 [retrofit2]()

#### REST란?

> Representational State Transfer의 약자로, 웹에서 사용하는 Architecture의 한 형식, 네트워크 상에서 클라이언트와 서버 간의 통신 방식

- HTTP URI로 잘 표현된 리소스에 대한 행위를 HTTP Method로 정의 리소스의 내용은 json, xml, yaml 등의 다양한 표현 언어로 정의
- HTTP에서는 GET, POST, PUT, DELETED 등의 Method를 제공




### 출처

- 출처 : http://thdev.tech/androiddev/2016/11/13/Android-Retrofit-Intro.html

## TODO

- retrofit 정리 git 만들기
- RX와 retrofit 합쳐서 사용하는 연습

## Retrospect

- 프로젝트할때 rx와 retrofit 이용해서 만들 예정이다.

## Output
- 생략
