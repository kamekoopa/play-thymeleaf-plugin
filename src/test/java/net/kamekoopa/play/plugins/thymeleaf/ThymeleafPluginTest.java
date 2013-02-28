package net.kamekoopa.play.plugins.thymeleaf;

import static org.hamcrest.CoreMatchers.is;
import static org.joox.JOOX.$;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joox.Match;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.xml.sax.SAXException;

import play.api.mvc.SimpleResult;
import play.api.templates.Html;
import play.test.Helpers;
import play.test.WithServer;

@RunWith(Enclosed.class)
public class ThymeleafPluginTest extends WithServer {


	final static List<String> plugins = new ArrayList<>();

	@BeforeClass
	public static void beforeClass() throws Exception {
		plugins.add("net.kamekoopa.play.plugins.thymeleaf.ThymeleafPlugin");
	}


	public static class プラグイン起動成功パターン extends WithServer {

		private Match expected;

		@Before
		public void setup() throws IOException, SAXException{

			InputStream in = this.getClass().getResourceAsStream("/index.html.expected");
			this.expected = $(in);

			Map<String, String> conf = new HashMap<>();
			conf.put("thymeleaf.enable", "true");
			conf.put("thymeleaf.views" , "src/test/resources/");
			conf.put("thymeleaf.suffix", ".html");
			conf.put("thymeleaf.mode"  , "HTML5");
			conf.put("thymeleaf.ttl"   , "60m");

			super.start(Helpers.fakeApplication(conf, plugins));
		}

		@Test
		public void テンプレートからHTMLが生成できるか() throws SAXException, IOException{

			Map<String, Object> vars = new HashMap<>();
			vars.put("message", "Hello");

			ThymeleafResult result = ThymeleafPlugin.generateResult("index", vars);
			InputStream in = new ByteArrayInputStream(result.html.toString().getBytes("UTF-8"));
			Match actual = $(in);

			@SuppressWarnings("unchecked")
			SimpleResult<Html> playOkResult = (SimpleResult<Html>) result.ok().getWrappedResult();


			assertThat(actual.find("body>div>span").text(),is(this.expected.find("body>div>span").text()));
			assertThat(playOkResult.header().status(),is(200));
		}

		@Test
		public void ステータスヘッダ200(){

			Map<String, Object> vars = new HashMap<>();
			vars.put("message", "Hello");

			@SuppressWarnings("unchecked")
			SimpleResult<Html> playOkResult = (SimpleResult<Html>) ThymeleafPlugin.generateResult("index", vars).ok().getWrappedResult();


			assertThat(playOkResult.header().status(), is(200));
		}

		@Test
		public void ステータスヘッダ201(){

			Map<String, Object> vars = new HashMap<>();
			vars.put("message", "Hello");

			@SuppressWarnings("unchecked")
			SimpleResult<Html> playOkResult = (SimpleResult<Html>) ThymeleafPlugin.generateResult("index", vars).created().getWrappedResult();


			assertThat(playOkResult.header().status(), is(201));
		}

		@Test
		public void ステータスヘッダ400(){

			Map<String, Object> vars = new HashMap<>();
			vars.put("message", "Hello");

			@SuppressWarnings("unchecked")
			SimpleResult<Html> playOkResult = (SimpleResult<Html>) ThymeleafPlugin.generateResult("index", vars).badRequest().getWrappedResult();


			assertThat(playOkResult.header().status(), is(400));
		}

		@Test
		public void ステータスヘッダ404(){

			Map<String, Object> vars = new HashMap<>();
			vars.put("message", "Hello");

			@SuppressWarnings("unchecked")
			SimpleResult<Html> playOkResult = (SimpleResult<Html>) ThymeleafPlugin.generateResult("index", vars).notFound().getWrappedResult();


			assertThat(playOkResult.header().status(), is(404));
		}

		@Test
		public void ステータスヘッダ500(){

			Map<String, Object> vars = new HashMap<>();
			vars.put("message", "Hello");

			@SuppressWarnings("unchecked")
			SimpleResult<Html> playOkResult = (SimpleResult<Html>) ThymeleafPlugin.generateResult("index", vars).internalServerError().getWrappedResult();


			assertThat(playOkResult.header().status(), is(500));
		}

		@Test
		public void ステータスヘッダ503(){

			Map<String, Object> vars = new HashMap<>();
			vars.put("message", "Hello");

			@SuppressWarnings("unchecked")
			SimpleResult<Html> playOkResult = (SimpleResult<Html>) ThymeleafPlugin.generateResult("index", vars).serviceUnavailable().getWrappedResult();


			assertThat(playOkResult.header().status(), is(503));
		}
	}

	public static class プラグイン無効化設定パターン extends WithServer {


		@Before
		public void setup() throws IOException, SAXException{

			Map<String, String> conf = new HashMap<>();
			conf.put("thymeleaf.enable", "false");
			conf.put("thymeleaf.views" , "src/test/resources/");
			conf.put("thymeleaf.suffix", ".html");
			conf.put("thymeleaf.mode"  , "HTML5");
			conf.put("thymeleaf.ttl"   , "60m");

			super.start(Helpers.fakeApplication(conf, plugins));
		}

		@Test(expected = NullPointerException.class)
		public void プラグイン機能呼び出し() {
			ThymeleafPlugin.generateResult("index", new HashMap<String, Object>());
		}
	}

	public static class プラグイン設定無し起動失敗パターン extends WithServer {


		@Before
		public void setup() throws IOException, SAXException{

			Map<String, String> conf = new HashMap<>();

			super.start(Helpers.fakeApplication(conf, plugins));
		}

		@Test(expected = NullPointerException.class)
		public void プラグイン機能呼び出し() {
			ThymeleafPlugin.generateResult("index", new HashMap<String, Object>());
		}
	}
}
