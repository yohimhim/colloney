// package colloney;
//
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Answers.RETURNS_DEEP_STUBS;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyLong;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;
//
// import java.lang.reflect.Field;
// import java.lang.reflect.Method;
// import java.math.BigDecimal;
// import java.time.Instant;
// import java.time.LocalDate;
// import java.util.List;
// import java.util.Map;
// import java.util.Optional;
//
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.*;
// import org.mockito.ArgumentCaptor;
// import org.springframework.dao.DataIntegrityViolationException;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageImpl;
// import org.springframework.data.domain.Pageable;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.test.util.ReflectionTestUtils;
// import org.springframework.web.client.RestClientException;
// import org.springframework.web.client.RestTemplate;
// import org.springframework.web.server.ResponseStatusException;
//
// import com.google.common.util.concurrent.RateLimiter;
//
// import colloney.ai.AiChat;
// import colloney.ai.AiController;
// import colloney.ai.AiRepository;
// import colloney.ai.AiRequest;
// import colloney.ai.AiResponse;
// import colloney.ai.AiService;
// import colloney.ai.OllamaResponseDto;
// import colloney.banking.account.Account;
// import colloney.banking.account.AccountRepository;
// import colloney.banking.account.AccountType;
// import colloney.budgeting.budget.*;
// import colloney.budgeting.category.*;
// import colloney.config.SpringContext;
// import colloney.exception.InvalidCredentialsException;
// import colloney.log.ConnectionLogService;
// import colloney.log.enums.ConnectionType;
// import colloney.log.enums.EventType;
// import colloney.news.*;
// import colloney.posts.Post;
// import colloney.posts.PostController;
// import colloney.posts.PostMapper;
// import colloney.posts.PostRepository;
// import colloney.posts.PostRequest;
// import colloney.posts.PostResponse;
// import colloney.posts.PostService;
// import colloney.posts.PostSocket;
// import colloney.stock.symbol.SymbolRepository;
// import colloney.user.*;
// import colloney.user.financialdata.*;
// import colloney.user.financialdata.enums.HousingStatus;
// import colloney.user.financialdata.enums.ResidencyStatus;
// import colloney.user.financialdata.enums.SchoolType;
// import colloney.util.ApiResponse;
// import jakarta.persistence.EntityNotFoundException;
// import jakarta.websocket.RemoteEndpoint;
// import jakarta.websocket.Session;
//
// public class MarcusSystemTest {
//
// @Mock
// private RestTemplate restTemplate;
//
// @Mock
// private UserRepository userRepository;
//
// @Mock
// private AiRepository aiRepository;
//
// @Mock
// private AiRequest aiRequest;
//
// @Mock
// private OllamaResponseDto ollamaResponse;
//
// @Mock
// private AiService aiServiceMock;
//
// @InjectMocks
// private AiService aiService;
//
// @InjectMocks
// private AiController aiController;
//
// private User user;
//
// @Mock
// private Session session;
//
// @Mock
// private RemoteEndpoint.Basic remoteEndpointBasic;
//
// @Mock
// private PostRepository postRepository;
//
// @Mock
// private ConnectionLogService connectionLogService;
//
// private MockedStatic<SpringContext> springContextMock;
//
// private PostSocket postSocket;
//
// @Mock
// private PostRequest postRequest;
//
// @Mock
// private User postAuthor;
//
// @Mock
// private Post post;
//
// @Mock
// private PostService postServiceMock;
//
// @InjectMocks
// private PostController postController;
//
// @InjectMocks
// private PostService postService;
//
// @Mock
// private FinancialDataRepository financialDataRepository;
//
// @InjectMocks
// private FinancialDataService financialDataService;
//
// @Mock
// private FinancialDataService financialDataServiceMock;
//
// @InjectMocks
// private FinancialDataController financialDataController;
//
// @Mock
// private BudgetRepository budgetRepository;
//
// @Mock
// private CategoryRepository categoryRepository;
//
// @Mock
// private CategoryRequest categoryRequest;
//
// @InjectMocks
// private CategoryService categoryService;
//
// @Mock
// private CreateBudgetRequest createBudgetRequest;
//
// @Mock
// private UpdateBudgetSpentRequest updateBudgetSpentRequest;
//
// @InjectMocks
// private BudgetService budgetService;
//
// @Mock
// private BudgetService budgetServiceMock;
//
// @InjectMocks
// private BudgetController budgetController;
//
// @Mock
// private CategoryService categoryServiceMock;
//
// @InjectMocks
// private CategoryController categoryController;
//
// @Mock
// private PasswordEncoder passwordEncoder;
//
// @Mock
// private AccountRepository accountRepository;
//
// @InjectMocks
// private UserService userService;
//
// @Mock
// private UserService userServiceMock;
//
// @InjectMocks
// private UserController userController;
//
// @InjectMocks
// private CustomUserDetailsService customUserDetailsService;
//
// @Mock
// private NewsRepository newsRepository;
//
// @InjectMocks
// private NewsService newsService;
//
// private MarketNewsScheduler marketNewsScheduler;
//
// @Mock
// private SymbolRepository symbolRepository;
//
// @Mock
// private RateLimiter rateLimiter;
//
// private CompanyNewsScheduler companyNewsScheduler;
//
// @Mock
// private NewsService newsServiceMock;
//
// @InjectMocks
// private NewsController newsController;
//
// @BeforeEach
// void setup() {
// MockitoAnnotations.openMocks(this);
// ReflectionTestUtils.setField(aiService, "ollamaBaseUrl", "http://ollama-host:11434");
// user = new User();
// when(aiRequest.getModel()).thenReturn("collonAI-speed");
// when(aiRequest.getPrompt()).thenReturn("Explain ETFs");
// when(ollamaResponse.getResponse()).thenReturn("ETF explanation");
//
// marketNewsScheduler = new MarketNewsScheduler(restTemplate, newsRepository);
// ReflectionTestUtils.setField(marketNewsScheduler, "apiKey", "KEY");
// ReflectionTestUtils.setField(marketNewsScheduler, "apiUrl", "http://api");
// ReflectionTestUtils.setField(marketNewsScheduler, "categories", List.of("general"));
//
// companyNewsScheduler = new CompanyNewsScheduler(restTemplate, newsRepository, symbolRepository, rateLimiter);
// ReflectionTestUtils.setField(companyNewsScheduler, "apiKey", "KEY");
// ReflectionTestUtils.setField(companyNewsScheduler, "apiUrl", "http://api");
//
// }
//
// @BeforeEach
// void setupPostSocketDependencies() {
// springContextMock = Mockito.mockStatic(SpringContext.class);
// postSocket = new PostSocket();
// }
//
// @AfterEach
// void tearDownPostSocketDependencies() {
// if (springContextMock != null) {
// springContextMock.close();
// springContextMock = null;
// }
// }
//
// @Test
// void generate_shouldCallOllamaSaveChatAndReturnResponse() {
// when(userRepository.findById(1L)).thenReturn(Optional.of(user));
// when(restTemplate.postForObject(anyString(), any(), eq(OllamaResponseDto.class))).thenReturn(ollamaResponse);
//
// ApiResponse<AiResponse> result = aiService.generate(1L, aiRequest);
//
// assertNotNull(result);
// assertEquals("Response Generated", result.getMessage());
// assertNotNull(result.getData());
// assertEquals("collonAI-speed", result.getData().getModel());
// assertEquals("Explain ETFs", result.getData().getPrompt());
// assertEquals("ETF explanation", result.getData().getResponse());
// assertNotNull(result.getData().getCreatedAt());
// verify(userRepository, times(1)).findById(1L);
// verify(restTemplate, times(1)).postForObject(anyString(), any(), eq(OllamaResponseDto.class));
// verify(aiRepository, times(1)).save(any(AiChat.class));
// }
//
// @Test
// void generate_shouldThrowNotFoundWhenUserMissing() {
// when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
//
// ResponseStatusException ex = assertThrows(ResponseStatusException.class,
// () -> aiService.generate(1L, aiRequest));
//
// assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
// verify(userRepository, times(1)).findById(1L);
// }
//
// @Test
// void generate_shouldThrowBadGatewayWhenOllamaResponseNull() {
// when(userRepository.findById(1L)).thenReturn(Optional.of(user));
// when(restTemplate.postForObject(anyString(), any(), eq(OllamaResponseDto.class))).thenReturn(null);
//
// ResponseStatusException ex = assertThrows(ResponseStatusException.class,
// () -> aiService.generate(1L, aiRequest));
//
// assertEquals(HttpStatus.BAD_GATEWAY, ex.getStatusCode());
// verify(aiRepository, times(0)).save(any(AiChat.class));
// }
//
// @Test
// void generate_shouldThrowBadGatewayWhenOllamaResponseBodyNull() {
// when(userRepository.findById(1L)).thenReturn(Optional.of(user));
// when(restTemplate.postForObject(anyString(), any(), eq(OllamaResponseDto.class))).thenReturn(ollamaResponse);
// when(ollamaResponse.getResponse()).thenReturn(null);
//
// ResponseStatusException ex = assertThrows(ResponseStatusException.class,
// () -> aiService.generate(1L, aiRequest));
//
// assertEquals(HttpStatus.BAD_GATEWAY, ex.getStatusCode());
// verify(aiRepository, times(0)).save(any(AiChat.class));
// }
//
// @Test
// void getChatsByUserId_shouldReturnMappedHistory() {
// when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
// AiChat chat = AiChat.builder().user(user).model("collonAI-speed").prompt("Explain ETFs")
// .response("ETF explanation").createdAt(Instant.now()).build();
//
// when(aiRepository.findByUser_IdOrderByCreatedAtDesc(1L)).thenReturn(List.of(chat));
//
// ApiResponse<List<AiResponse>> result = aiService.getChatsByUserId(1L);
//
// assertNotNull(result);
// assertEquals("History fetched successfully", result.getMessage());
// assertNotNull(result.getData());
// assertEquals(1, result.getData().size());
// AiResponse r = result.getData().get(0);
// assertEquals(chat.getModel(), r.getModel());
// assertEquals(chat.getPrompt(), r.getPrompt());
// assertEquals(chat.getResponse(), r.getResponse());
// assertEquals(chat.getCreatedAt(), r.getCreatedAt());
// }
//
// @Test
// void getChatsByUserId_shouldThrowWhenUserNotFound() {
// when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
// assertThrows(EntityNotFoundException.class, () -> aiService.getChatsByUserId(1L));
// }
//
// @Test
// void deleteChatsByUserId_shouldDeleteAndReturnOk() {
// ApiResponse<Void> result = aiService.deleteChatsByUserId(5L);
//
// assertNotNull(result);
// assertEquals("Deleted chats for user 5 successfully", result.getMessage());
// assertNull(result.getData());
// verify(aiRepository, times(1)).deleteByUserId(5L);
// }
//
// @Test
// void generate_shouldReturnWrappedResponse() {
// ApiResponse<AiResponse> response = ApiResponse.ok("ok", AiResponse.builder().model("m").build());
// when(aiServiceMock.generate(1L, aiRequest)).thenReturn(response);
//
// ResponseEntity<ApiResponse<AiResponse>> result = aiController.generate(1L, aiRequest);
//
// assertEquals(response, result.getBody());
// }
//
// @Test
// void getChatsByUserId_shouldReturnHistory() {
// ApiResponse<List<AiResponse>> response = ApiResponse.ok("ok", List.of());
// when(aiServiceMock.getChatsByUserId(1L)).thenReturn(response);
//
// ResponseEntity<ApiResponse<List<AiResponse>>> result = aiController.getChatsByUserId(1L);
//
// assertEquals(response, result.getBody());
// }
//
// @Test
// void deleteChatsByUserId_shouldReturnOk() {
// ApiResponse<Void> response = ApiResponse.ok("ok", null);
// when(aiServiceMock.deleteChatsByUserId(1L)).thenReturn(response);
//
// ResponseEntity<ApiResponse<Void>> result = aiController.deleteChatsByUserId(1L);
//
// assertEquals(response, result.getBody());
// }
//
// private Map<Session, String> getSessionUsernameMap() throws Exception {
// Field field = PostSocket.class.getDeclaredField("sessionUsernameMap");
// field.setAccessible(true);
// return (Map<Session, String>) field.get(null);
// }
//
// private Map<String, Session> getUsernameSessionMap() throws Exception {
// Field field = PostSocket.class.getDeclaredField("usernameSessionMap");
// field.setAccessible(true);
// return (Map<String, Session>) field.get(null);
// }
//
// private Map<String, Object> invokeToPostDto(Post p) throws Exception {
// Method m = PostSocket.class.getDeclaredMethod("toPostDto", Post.class);
// m.setAccessible(true);
// return (Map<String, Object>) m.invoke(null, p);
// }
//
// @Test
// void onOpen_shouldRegisterSessionAndLog() throws Exception {
// springContextMock.when(() -> SpringContext.getBean(ConnectionLogService.class))
// .thenReturn(connectionLogService);
// when(session.getId()).thenReturn("sess-1");
//
// postSocket.onOpen(session, "1", "marcus");
//
// Map<Session, String> sessionMap = getSessionUsernameMap();
// Map<String, Session> usernameMap = getUsernameSessionMap();
//
// assertEquals("marcus", sessionMap.get(session));
// assertEquals(session, usernameMap.get("marcus"));
// verify(connectionLogService).logConnection("1", "marcus", ConnectionType.POST, EventType.CONNECT);
// }
//
// @Test
// void onClose_shouldUnregisterSessionAndLog() throws Exception {
// springContextMock.when(() -> SpringContext.getBean(ConnectionLogService.class))
// .thenReturn(connectionLogService);
// when(session.getId()).thenReturn("sess-1");
//
// postSocket.onOpen(session, "1", "marcus");
// postSocket.onClose(session, "1");
//
// Map<Session, String> sessionMap = getSessionUsernameMap();
// Map<String, Session> usernameMap = getUsernameSessionMap();
//
// assertFalse(sessionMap.containsKey(session));
// assertFalse(usernameMap.containsKey("marcus"));
// verify(connectionLogService).logConnection("1", "marcus", ConnectionType.POST, EventType.DISCONNECT);
// }
//
// @Test
// void onError_shouldNotThrow() {
// Throwable t = new RuntimeException("test error");
// assertDoesNotThrow(() -> postSocket.onError(session, t));
// }
//
// @Test
// void postMapper_toEntity_shouldMapFields() {
// when(postRequest.getContent()).thenReturn("hello world");
//
// Post result = PostMapper.toEntity(postRequest, postAuthor);
//
// assertEquals(postAuthor, result.getAuthor());
// assertEquals("hello world", result.getContent());
// }
//
// @Test
// void postMapper_toResponse_shouldMapAllFields() {
// when(post.getId()).thenReturn(10L);
// when(post.getContent()).thenReturn("hi");
// when(post.getCreatedAt()).thenReturn(Instant.now());
// when(post.getUpdatedAt()).thenReturn(Instant.now());
// when(post.getAuthor()).thenReturn(postAuthor);
// when(postAuthor.getId()).thenReturn(5L);
// when(postAuthor.getUsername()).thenReturn("marcus");
//
// PostResponse res = PostMapper.toResponse(post);
//
// assertEquals(10L, res.getId());
// assertEquals("hi", res.getContent());
// assertEquals(5L, res.getAuthor().getId());
// assertEquals("marcus", res.getAuthor().getUsername());
// assertNotNull(res.getCreatedAt());
// assertNotNull(res.getUpdatedAt());
// }
//
// @Test
// void postMapper_toResponse_shouldThrowWhenAuthorNull() {
// when(post.getAuthor()).thenReturn(null);
// assertThrows(NullPointerException.class, () -> PostMapper.toResponse(post));
// }
//
// @Test
// void getAllPosts_shouldReturnWrappedResponse() {
// ApiResponse<List<PostResponse>> response = ApiResponse.ok("ok", List.of());
// when(postServiceMock.getAllPosts()).thenReturn(response);
//
// ResponseEntity<ApiResponse<List<PostResponse>>> result = postController.getAllPosts();
//
// assertEquals(response, result.getBody());
// }
//
// @Test
// void createPost_shouldReturnWrappedResponse() {
// PostResponse postResponse = PostResponse.builder().id(1L).build();
// ApiResponse<PostResponse> response = ApiResponse.ok("ok", postResponse);
// when(postServiceMock.createPost(postRequest)).thenReturn(response);
//
// ResponseEntity<ApiResponse<PostResponse>> result = postController.createPost(postRequest);
//
// assertEquals(response, result.getBody());
// }
//
// @Test
// void deleteOwnPost_shouldReturnWrappedResponse() {
// PostResponse postResponse = PostResponse.builder().id(2L).build();
// ApiResponse<PostResponse> response = ApiResponse.ok("ok", postResponse);
// when(postServiceMock.deleteOwnPost(2L)).thenReturn(response);
//
// ResponseEntity<ApiResponse<PostResponse>> result = postController.deleteOwnPost(2L);
//
// assertEquals(response, result.getBody());
// }
//
// @Test
// void updateOwnPost_shouldReturnWrappedResponse() {
// when(postRequest.getAuthorId()).thenReturn(5L);
// when(postRequest.getContent()).thenReturn("updated");
//
// PostResponse postResponse = PostResponse.builder().id(3L).build();
// ApiResponse<PostResponse> response = ApiResponse.ok("ok", postResponse);
// when(postServiceMock.updateOwnPost(3L, 5L, "updated")).thenReturn(response);
//
// ResponseEntity<ApiResponse<PostResponse>> result = postController.updateOwnPost(3L, postRequest);
//
// assertEquals(response, result.getBody());
// }
//
// @Test
// void getAllPosts_shouldReturnListResponse() {
// Post entity = Mockito.mock(Post.class);
// PostResponse mapped = PostResponse.builder().id(10L).build();
//
// when(postRepository.findAll()).thenReturn(List.of(entity));
//
// try (MockedStatic<PostMapper> postMapperMock = Mockito.mockStatic(PostMapper.class)) {
// postMapperMock.when(() -> PostMapper.toResponse(entity)).thenReturn(mapped);
//
// ApiResponse<List<PostResponse>> result = postService.getAllPosts();
//
// assertEquals("Posts retrieved successfully", result.getMessage());
// assertNotNull(result.getData());
// assertEquals(1, result.getData().size());
// assertEquals(mapped, result.getData().get(0));
// }
// }
//
// @Test
// void createPost_shouldCreateAndBroadcast() {
// when(postRequest.getAuthorId()).thenReturn(5L);
// User author = new User();
// when(userRepository.findById(5L)).thenReturn(Optional.of(author));
//
// Post entity = Mockito.mock(Post.class);
// Post saved = Mockito.mock(Post.class);
// PostResponse mapped = PostResponse.builder().id(1L).build();
//
// when(postRepository.save(entity)).thenReturn(saved);
//
// try (MockedStatic<PostMapper> postMapperMock = Mockito.mockStatic(PostMapper.class);
// MockedStatic<PostSocket> postSocketMock = Mockito.mockStatic(PostSocket.class)) {
//
// postMapperMock.when(() -> PostMapper.toEntity(postRequest, author)).thenReturn(entity);
// postMapperMock.when(() -> PostMapper.toResponse(saved)).thenReturn(mapped);
//
// ApiResponse<PostResponse> result = postService.createPost(postRequest);
//
// assertEquals("Post created successfully", result.getMessage());
// assertEquals(mapped, result.getData());
//
// postSocketMock.verify(PostSocket::broadcastUpdatedPosts);
// verify(postRepository).save(entity);
// }
// }
//
// @Test
// void createPost_shouldThrowWhenUserNotFound() {
// when(postRequest.getAuthorId()).thenReturn(5L);
// when(userRepository.findById(5L)).thenReturn(Optional.empty());
//
// assertThrows(EntityNotFoundException.class, () -> postService.createPost(postRequest));
// }
//
// @Test
// void deleteOwnPost_shouldDeleteAndBroadcast() {
// Post postEntity = Mockito.mock(Post.class);
// PostResponse mapped = PostResponse.builder().id(2L).build();
//
// when(postRepository.findById(2L)).thenReturn(Optional.of(postEntity));
//
// try (MockedStatic<PostMapper> postMapperMock = Mockito.mockStatic(PostMapper.class);
// MockedStatic<PostSocket> postSocketMock = Mockito.mockStatic(PostSocket.class)) {
//
// postMapperMock.when(() -> PostMapper.toResponse(postEntity)).thenReturn(mapped);
//
// ApiResponse<PostResponse> result = postService.deleteOwnPost(2L);
//
// assertEquals("Post deleted successfully", result.getMessage());
// assertEquals(mapped, result.getData());
//
// verify(postRepository).delete(postEntity);
// postSocketMock.verify(PostSocket::broadcastUpdatedPosts);
// }
// }
//
// @Test
// void deleteOwnPost_shouldThrowWhenPostNotFound() {
// when(postRepository.findById(99L)).thenReturn(Optional.empty());
//
// assertThrows(EntityNotFoundException.class, () -> postService.deleteOwnPost(99L));
// }
//
// @Test
// void updateOwnPost_shouldUpdateSaveAndBroadcast() {
// Post postEntity = Mockito.mock(Post.class, RETURNS_DEEP_STUBS);
// User author = Mockito.mock(User.class);
// when(postEntity.getAuthor()).thenReturn(author);
// when(author.getId()).thenReturn(5L);
// when(postRepository.findById(3L)).thenReturn(Optional.of(postEntity));
//
// Post updatedPost = Mockito.mock(Post.class);
// when(postEntity.toBuilder().content("new content").build()).thenReturn(updatedPost);
//
// PostResponse mapped = PostResponse.builder().id(3L).build();
//
// try (MockedStatic<PostMapper> postMapperMock = Mockito.mockStatic(PostMapper.class);
// MockedStatic<PostSocket> postSocketMock = Mockito.mockStatic(PostSocket.class)) {
//
// postMapperMock.when(() -> PostMapper.toResponse(updatedPost)).thenReturn(mapped);
//
// ApiResponse<PostResponse> result = postService.updateOwnPost(3L, 5L, "new content");
//
// assertEquals("Post updated successfully", result.getMessage());
// assertEquals(mapped, result.getData());
//
// verify(postRepository).save(updatedPost);
// postSocketMock.verify(PostSocket::broadcastUpdatedPosts);
// }
// }
//
// @Test
// void updateOwnPost_shouldThrowWhenPostNotFound() {
// when(postRepository.findById(3L)).thenReturn(Optional.empty());
//
// assertThrows(IllegalArgumentException.class, () -> postService.updateOwnPost(3L, 5L, "x"));
// }
//
// @Test
// void updateOwnPost_shouldThrowWhenUserNotAuthor() {
// Post postEntity = Mockito.mock(Post.class, RETURNS_DEEP_STUBS);
// User author = Mockito.mock(User.class);
// when(postEntity.getAuthor()).thenReturn(author);
// when(author.getId()).thenReturn(7L);
// when(postRepository.findById(3L)).thenReturn(Optional.of(postEntity));
//
// IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
// () -> postService.updateOwnPost(3L, 5L, "x"));
//
// assertEquals("You can only edit your own posts.", ex.getMessage());
// }
//
// @Test
// void broadcastUpdatedPosts_sendsToOpenSessions_withoutError() throws Exception {
// springContextMock.when(() -> SpringContext.getBean(PostRepository.class)).thenReturn(postRepository);
//
// Post p = Mockito.mock(Post.class);
// when(postRepository.findAll()).thenReturn(List.of(p));
//
// Map<Session, String> sessionMap = getSessionUsernameMap();
// sessionMap.clear();
// sessionMap.put(session, "marcus");
//
// when(session.isOpen()).thenReturn(true);
// when(session.getBasicRemote()).thenReturn(remoteEndpointBasic);
//
// assertDoesNotThrow(PostSocket::broadcastUpdatedPosts);
// }
//
// @Test
// void broadcastUpdatedPosts_skipsClosedSessions_withoutError() throws Exception {
// springContextMock.when(() -> SpringContext.getBean(PostRepository.class)).thenReturn(postRepository);
//
// when(postRepository.findAll()).thenReturn(List.of());
//
// Map<Session, String> sessionMap = getSessionUsernameMap();
// sessionMap.clear();
// sessionMap.put(session, "marcus");
//
// when(session.isOpen()).thenReturn(false);
//
// assertDoesNotThrow(PostSocket::broadcastUpdatedPosts);
// }
//
// @Test
// void broadcastUpdatedPosts_handlesExceptionInTryBlock() throws Exception {
// springContextMock.when(() -> SpringContext.getBean(PostRepository.class)).thenReturn(postRepository);
//
// when(postRepository.findAll()).thenThrow(new RuntimeException("boom"));
//
// assertDoesNotThrow(PostSocket::broadcastUpdatedPosts);
// }
//
// @Test
// void toPostDto_handlesNullOptionalFields() throws Exception {
// Post p = Mockito.mock(Post.class);
//
// when(p.getCreatedAt()).thenReturn(null);
// when(p.getUpdatedAt()).thenReturn(null);
// when(p.getAuthor()).thenReturn(null);
//
// Map<String, Object> dto = invokeToPostDto(p);
//
// assertNotNull(dto);
// }
//
// @Test
// void toPostDto_handlesNonNullOptionalFields() throws Exception {
// Post p = Mockito.mock(Post.class);
// User author = Mockito.mock(User.class);
//
// when(p.getCreatedAt()).thenReturn(Instant.now());
// when(p.getUpdatedAt()).thenReturn(Instant.now());
// when(p.getAuthor()).thenReturn(author);
// when(author.getId()).thenReturn(5L);
// when(author.getUsername()).thenReturn("marcus");
//
// Map<String, Object> dto = invokeToPostDto(p);
//
// assertNotNull(dto);
// }
//
// @Test
// void getByUserId_shouldReturnFinancialData() {
// FinancialData data = new FinancialData();
// when(financialDataRepository.findById(1L)).thenReturn(Optional.of(data));
//
// ApiResponse<FinancialDataResponse> result = financialDataService.getByUserId(1L);
//
// assertEquals("Financial data retrieved successfully", result.getMessage());
// assertNotNull(result.getData());
// }
//
// @Test
// void getByUserId_shouldThrowWhenNotFound() {
// when(financialDataRepository.findById(1L)).thenReturn(Optional.empty());
//
// assertThrows(EntityNotFoundException.class, () -> financialDataService.getByUserId(1L));
// }
//
// @Test
// void createUserFinancialData_shouldCreateWhenNotExisting() {
// Long userId = 5L;
// FinancialData body = new FinancialData();
// User user = new User();
//
// when(financialDataRepository.existsById(userId)).thenReturn(false);
// when(userRepository.findById(userId)).thenReturn(Optional.of(user));
// when(financialDataRepository.save(any(FinancialData.class)))
// .thenAnswer(inv -> inv.getArgument(0, FinancialData.class));
//
// ApiResponse<FinancialDataResponse> result = financialDataService.createUserFinancialData(userId, body);
//
// assertEquals("Financial data created successfully", result.getMessage());
// assertNotNull(result.getData());
// verify(financialDataRepository).save(any(FinancialData.class));
// }
//
// @Test
// void createUserFinancialData_shouldThrowWhenAlreadyExists() {
// Long userId = 5L;
// FinancialData body = new FinancialData();
//
// when(financialDataRepository.existsById(userId)).thenReturn(true);
//
// assertThrows(DataIntegrityViolationException.class,
// () -> financialDataService.createUserFinancialData(userId, body));
// }
//
// @Test
// void updateUserFinancialData_shouldUpdateAndSave() {
// Long userId = 7L;
// FinancialData existing = new FinancialData();
// FinancialData body = FinancialData.builder().user(new User()).buyingPowerBalance(BigDecimal.valueOf(100))
// .school("ISU").residencyStatus(ResidencyStatus.IN_STATE).housingStatus(HousingStatus.COMMUTER)
// .otherExpenses(BigDecimal.valueOf(50)).savingsGoal(BigDecimal.valueOf(1000))
// .savingsGoalDueDate(LocalDate.parse("2025-12-12")).scholarships(BigDecimal.valueOf(200))
// .schoolType(SchoolType.PUBLIC).build();
//
// when(financialDataRepository.findById(userId)).thenReturn(Optional.of(existing));
// when(financialDataRepository.save(any(FinancialData.class)))
// .thenAnswer(inv -> inv.getArgument(0, FinancialData.class));
//
// ApiResponse<FinancialDataResponse> result = financialDataService.updateUserFinancialData(userId, body);
//
// assertEquals("Financial data has been updated for userId " + userId, result.getMessage());
// assertNotNull(result.getData());
// }
//
// @Test
// void updateUserFinancialData_shouldThrowWhenNotFound() {
// Long userId = 7L;
// FinancialData body = new FinancialData();
//
// when(financialDataRepository.findById(userId)).thenReturn(Optional.empty());
//
// assertThrows(EntityNotFoundException.class, () -> financialDataService.updateUserFinancialData(userId, body));
// }
//
// @Test
// void deleteUserFinancialData_shouldDeleteAndReturn() {
// Long userId = 9L;
// FinancialData existing = new FinancialData();
//
// when(financialDataRepository.findById(userId)).thenReturn(Optional.of(existing));
//
// ApiResponse<FinancialDataResponse> result = financialDataService.deleteUserFinancialData(userId);
//
// assertEquals("UserId " + userId + "'s Financial data has been deleted", result.getMessage());
// assertNotNull(result.getData());
// verify(financialDataRepository).delete(existing);
// }
//
// @Test
// void deleteUserFinancialData_shouldThrowWhenNotFound() {
// Long userId = 9L;
// when(financialDataRepository.findById(userId)).thenReturn(Optional.empty());
//
// assertThrows(EntityNotFoundException.class, () -> financialDataService.deleteUserFinancialData(userId));
// }
//
// @Test
// void getFinancialData_shouldReturnWrappedResponse() {
// Long userId = 1L;
// FinancialDataResponse data = FinancialDataResponse.builder().build();
// ApiResponse<FinancialDataResponse> response = ApiResponse.ok("ok", data);
//
// when(financialDataServiceMock.getByUserId(userId)).thenReturn(response);
//
// ResponseEntity<ApiResponse<FinancialDataResponse>> result = financialDataController.getFinancialData(userId);
//
// assertEquals(response, result.getBody());
// }
//
// @Test
// void createUserFinancialData_shouldReturnWrappedResponse() {
// Long userId = 2L;
// FinancialData body = new FinancialData();
// FinancialDataResponse created = FinancialDataResponse.builder().build();
// ApiResponse<FinancialDataResponse> response = ApiResponse.ok("created", created);
//
// when(financialDataServiceMock.createUserFinancialData(userId, body)).thenReturn(response);
//
// ResponseEntity<ApiResponse<FinancialDataResponse>> result = financialDataController
// .createUserFinancialData(userId, body);
//
// assertEquals(response, result.getBody());
// }
//
// @Test
// void updateUserFinancialData_shouldReturnWrappedResponse() {
// Long userId = 3L;
// FinancialData body = new FinancialData();
// FinancialDataResponse updated = FinancialDataResponse.builder().build();
// ApiResponse<FinancialDataResponse> response = ApiResponse.ok("updated", updated);
//
// when(financialDataServiceMock.updateUserFinancialData(userId, body)).thenReturn(response);
//
// ResponseEntity<ApiResponse<FinancialDataResponse>> result = financialDataController
// .updateUserFinancialData(userId, body);
//
// assertEquals(response, result.getBody());
// }
//
// @Test
// void deleteUserFinancialData_shouldReturnWrappedResponse() {
// Long userId = 4L;
// FinancialDataResponse deleted = FinancialDataResponse.builder().build();
// ApiResponse<FinancialDataResponse> response = ApiResponse.ok("deleted", deleted);
//
// when(financialDataServiceMock.deleteUserFinancialData(userId)).thenReturn(response);
//
// ResponseEntity<ApiResponse<FinancialDataResponse>> result = financialDataController
// .deleteUserFinancialData(userId);
//
// assertEquals(response, result.getBody());
// }
//
// @Test
// void budgetMapper_toEntity_shouldMapFields() {
// CreateBudgetRequest req = Mockito.mock(CreateBudgetRequest.class);
// Category cat = Mockito.mock(Category.class);
// User user = new User();
//
// when(req.getName()).thenReturn("Food Budget");
// when(req.getLimitAmount()).thenReturn(250.0);
// when(req.getStartDate()).thenReturn(LocalDate.of(2025, 1, 1));
// when(req.getEndDate()).thenReturn(LocalDate.of(2025, 1, 31));
//
// Budget b = BudgetMapper.toEntity(req, cat, user);
//
// assertEquals(user, b.getUser());
// assertEquals(cat, b.getCategory());
// assertEquals("Food Budget", b.getName());
// assertEquals(250.0, b.getLimitAmount());
// assertEquals(0.0, b.getSpentAmount());
// assertEquals(LocalDate.of(2025, 1, 1), b.getStartDate());
// assertEquals(LocalDate.of(2025, 1, 31), b.getEndDate());
// }
//
// @Test
// void budgetMapper_toResponse_shouldMapAllFields() {
// Budget budget = Mockito.mock(Budget.class);
// Category cat = Mockito.mock(Category.class);
//
// when(budget.getId()).thenReturn(10L);
// when(budget.getCategory()).thenReturn(cat);
// when(cat.getId()).thenReturn(3L);
// when(cat.getName()).thenReturn("Food");
// when(budget.getName()).thenReturn("January Food Budget");
// when(budget.getLimitAmount()).thenReturn(300.0);
// when(budget.getSpentAmount()).thenReturn(50.0);
// when(budget.getStartDate()).thenReturn(LocalDate.of(2025, 1, 1));
// when(budget.getEndDate()).thenReturn(LocalDate.of(2025, 1, 31));
//
// BudgetResponse res = BudgetMapper.toResponse(budget);
//
// assertEquals(10L, res.getId());
// assertEquals(3L, res.getCategoryId());
// assertEquals("Food", res.getCategoryName());
// assertEquals("January Food Budget", res.getName());
// assertEquals(300.0, res.getLimitAmount());
// assertEquals(50.0, res.getSpentAmount());
// assertEquals(LocalDate.of(2025, 1, 1), res.getStartDate());
// assertEquals(LocalDate.of(2025, 1, 31), res.getEndDate());
// }
//
// @Test
// void budgetMapper_toResponse_shouldThrowWhenCategoryNull() {
// Budget b = Mockito.mock(Budget.class);
// when(b.getCategory()).thenReturn(null);
//
// assertThrows(NullPointerException.class, () -> BudgetMapper.toResponse(b));
// }
//
// @Test
// void getBudgetsForUser_shouldReturnListResponse() {
// Budget entity = Mockito.mock(Budget.class);
// when(budgetRepository.findByUser_Id(1L)).thenReturn(List.of(entity));
//
// BudgetResponse mapped = BudgetResponse.builder().id(10L).build();
//
// try (MockedStatic<BudgetMapper> mapperMock = Mockito.mockStatic(BudgetMapper.class)) {
// mapperMock.when(() -> BudgetMapper.toResponse(entity)).thenReturn(mapped);
//
// ApiResponse<List<BudgetResponse>> result = budgetService.getBudgetsForUser(1L);
//
// assertEquals("Budgets retrieved successfully", result.getMessage());
// assertNotNull(result.getData());
// assertEquals(1, result.getData().size());
// assertEquals(mapped, result.getData().get(0));
// }
// }
//
// @Test
// void createBudget_shouldCreateAndReturnResponse() {
// Long userId = 5L;
// Long categoryId = 3L;
//
// User userEntity = user;
// Category category = Mockito.mock(Category.class);
// Budget budgetEntity = Mockito.mock(Budget.class);
// Budget savedBudget = Mockito.mock(Budget.class);
// BudgetResponse mapped = BudgetResponse.builder().id(1L).build();
//
// when(createBudgetRequest.getCategoryId()).thenReturn(categoryId);
// when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
// when(categoryRepository.findByIdAndUser_Id(categoryId, userId)).thenReturn(Optional.of(category));
// when(budgetRepository.save(budgetEntity)).thenReturn(savedBudget);
//
// try (MockedStatic<BudgetMapper> mapperMock = Mockito.mockStatic(BudgetMapper.class)) {
// mapperMock.when(() -> BudgetMapper.toEntity(createBudgetRequest, category, userEntity))
// .thenReturn(budgetEntity);
// mapperMock.when(() -> BudgetMapper.toResponse(savedBudget)).thenReturn(mapped);
//
// ApiResponse<BudgetResponse> result = budgetService.createBudget(userId, createBudgetRequest);
//
// assertEquals("Budget created successfully", result.getMessage());
// assertEquals(mapped, result.getData());
// verify(budgetRepository).save(budgetEntity);
// }
// }
//
// @Test
// void createBudget_shouldThrowWhenUserNotFound() {
// Long userId = 5L;
// when(userRepository.findById(userId)).thenReturn(Optional.empty());
//
// assertThrows(EntityNotFoundException.class, () -> budgetService.createBudget(userId, createBudgetRequest));
// }
//
// @Test
// void createBudget_shouldThrowWhenCategoryNotFound() {
// Long userId = 5L;
// Long categoryId = 3L;
//
// when(userRepository.findById(userId)).thenReturn(Optional.of(user));
// when(createBudgetRequest.getCategoryId()).thenReturn(categoryId);
// when(categoryRepository.findByIdAndUser_Id(categoryId, userId)).thenReturn(Optional.empty());
//
// assertThrows(EntityNotFoundException.class, () -> budgetService.createBudget(userId, createBudgetRequest));
// }
//
// @Test
// void updateBudgetSpent_shouldUpdateAndReturnResponse() {
// Long budgetId = 10L;
//
// Budget budgetEntity = Mockito.mock(Budget.class);
// Budget savedBudget = Mockito.mock(Budget.class);
// BudgetResponse mapped = BudgetResponse.builder().id(budgetId).build();
//
// when(budgetRepository.findById(budgetId)).thenReturn(Optional.of(budgetEntity));
// when(budgetEntity.getSpentAmount()).thenReturn(100.0);
// when(updateBudgetSpentRequest.getSpentAmount()).thenReturn(50.0);
// when(budgetRepository.save(budgetEntity)).thenReturn(savedBudget);
//
// try (MockedStatic<BudgetMapper> mapperMock = Mockito.mockStatic(BudgetMapper.class)) {
// mapperMock.when(() -> BudgetMapper.toResponse(savedBudget)).thenReturn(mapped);
//
// ApiResponse<BudgetResponse> result = budgetService.updateBudgetSpent(budgetId, updateBudgetSpentRequest);
//
// assertEquals("Budget spent amount updated successfully", result.getMessage());
// assertEquals(mapped, result.getData());
//
// verify(budgetEntity).setSpentAmount(150.0);
// verify(budgetRepository).save(budgetEntity);
// }
// }
//
// @Test
// void updateBudgetSpent_shouldThrowWhenBudgetNotFound() {
// Long budgetId = 10L;
// when(budgetRepository.findById(budgetId)).thenReturn(Optional.empty());
//
// assertThrows(EntityNotFoundException.class,
// () -> budgetService.updateBudgetSpent(budgetId, updateBudgetSpentRequest));
// }
//
// @Test
// void deleteBudget_shouldDeleteAndReturnOk() {
// Long budgetId = 12L;
// Budget budgetEntity = Mockito.mock(Budget.class);
//
// when(budgetRepository.findById(budgetId)).thenReturn(Optional.of(budgetEntity));
//
// ApiResponse<Void> result = budgetService.deleteBudget(budgetId);
//
// assertEquals("Budget deleted successfully", result.getMessage());
// assertNull(result.getData());
// verify(budgetRepository).delete(budgetEntity);
// }
//
// @Test
// void deleteBudget_shouldThrowWhenBudgetNotFound() {
// Long budgetId = 12L;
// when(budgetRepository.findById(budgetId)).thenReturn(Optional.empty());
//
// assertThrows(EntityNotFoundException.class, () -> budgetService.deleteBudget(budgetId));
// }
//
// @Test
// void getBudgetsForUser_shouldReturnWrappedResponse() {
// Long userId = 1L;
// ApiResponse<List<BudgetResponse>> response = ApiResponse.ok("ok",
// List.of(BudgetResponse.builder().id(10L).build()));
//
// when(budgetServiceMock.getBudgetsForUser(userId)).thenReturn(response);
//
// ResponseEntity<ApiResponse<List<BudgetResponse>>> result = budgetController.getBudgetsForUser(userId);
//
// assertEquals(response, result.getBody());
// }
//
// @Test
// void createBudget_shouldReturnWrappedResponse() {
// Long userId = 2L;
// CreateBudgetRequest request = new CreateBudgetRequest();
// BudgetResponse budgetResponse = BudgetResponse.builder().id(20L).build();
// ApiResponse<BudgetResponse> response = ApiResponse.ok("created", budgetResponse);
//
// when(budgetServiceMock.createBudget(userId, request)).thenReturn(response);
//
// ResponseEntity<ApiResponse<BudgetResponse>> result = budgetController.createBudget(userId, request);
//
// assertEquals(response, result.getBody());
// }
//
// @Test
// void updateBudgetSpent_shouldReturnWrappedResponse() {
// Long budgetId = 3L;
// UpdateBudgetSpentRequest request = new UpdateBudgetSpentRequest();
// BudgetResponse budgetResponse = BudgetResponse.builder().id(budgetId).build();
// ApiResponse<BudgetResponse> response = ApiResponse.ok("updated", budgetResponse);
//
// when(budgetServiceMock.updateBudgetSpent(budgetId, request)).thenReturn(response);
//
// ResponseEntity<ApiResponse<BudgetResponse>> result = budgetController.updateBudgetSpent(budgetId, request);
//
// assertEquals(response, result.getBody());
// }
//
// @Test
// void deleteBudget_shouldReturnWrappedResponse() {
// Long budgetId = 4L;
// ApiResponse<Void> response = ApiResponse.ok("deleted", null);
//
// when(budgetServiceMock.deleteBudget(budgetId)).thenReturn(response);
//
// ResponseEntity<ApiResponse<Void>> result = budgetController.deleteBudget(budgetId);
//
// assertEquals(response, result.getBody());
// }
//
// @Test
// void categoryMapper_toEntity_shouldMapFields() {
// CategoryRequest request = Mockito.mock(CategoryRequest.class);
// User user = new User();
//
// when(request.getName()).thenReturn("Food");
// when(request.getIcon()).thenReturn("🍔");
//
// Category result = CategoryMapper.toEntity(request, user);
//
// assertEquals("Food", result.getName());
// assertEquals("🍔", result.getIcon());
// assertEquals(user, result.getUser());
// }
//
// @Test
// void categoryMapper_toResponse_shouldMapAllFields() {
// Category category = Mockito.mock(Category.class);
// User user = Mockito.mock(User.class);
//
// when(category.getId()).thenReturn(10L);
// when(category.getName()).thenReturn("Bills");
// when(category.getIcon()).thenReturn("💡");
// when(category.getUser()).thenReturn(user);
// when(user.getId()).thenReturn(5L);
//
// CategoryResponse res = CategoryMapper.toResponse(category);
//
// assertEquals(5L, res.getUserId());
// assertEquals(10L, res.getCategoryId());
// assertEquals("Bills", res.getName());
// assertEquals("💡", res.getIcon());
// }
//
// @Test
// void categoryMapper_toResponse_shouldThrowWhenUserNull() {
// Category category = Mockito.mock(Category.class);
// when(category.getUser()).thenReturn(null);
//
// assertThrows(NullPointerException.class, () -> CategoryMapper.toResponse(category));
// }
//
// @Test
// void createCategory_shouldCreateAndReturnResponse() {
// Long userId = 5L;
// User user = new User();
// Category categoryEntity = Mockito.mock(Category.class);
// Category savedCategory = Mockito.mock(Category.class);
// CategoryResponse mapped = CategoryResponse.builder().categoryId(100L).build();
//
// when(userRepository.findById(userId)).thenReturn(Optional.of(user));
// when(categoryRepository.save(categoryEntity)).thenReturn(savedCategory);
//
// try (MockedStatic<CategoryMapper> mapperMock = Mockito.mockStatic(CategoryMapper.class)) {
// mapperMock.when(() -> CategoryMapper.toEntity(categoryRequest, user)).thenReturn(categoryEntity);
// mapperMock.when(() -> CategoryMapper.toResponse(savedCategory)).thenReturn(mapped);
//
// ApiResponse<CategoryResponse> result = categoryService.createCategory(userId, categoryRequest);
//
// assertEquals("Category created successfully", result.getMessage());
// assertEquals(mapped, result.getData());
// verify(categoryRepository).save(categoryEntity);
// }
// }
//
// @Test
// void createCategory_shouldThrowWhenUserNotFound() {
// when(userRepository.findById(5L)).thenReturn(Optional.empty());
//
// assertThrows(EntityNotFoundException.class, () -> categoryService.createCategory(5L, categoryRequest));
// }
//
// @Test
// void getAllCategories_shouldReturnListResponse() {
// Category c = Mockito.mock(Category.class);
// CategoryResponse mapped = CategoryResponse.builder().categoryId(10L).build();
//
// when(categoryRepository.findAll()).thenReturn(List.of(c));
//
// try (MockedStatic<CategoryMapper> mapperMock = Mockito.mockStatic(CategoryMapper.class)) {
// mapperMock.when(() -> CategoryMapper.toResponse(c)).thenReturn(mapped);
//
// ApiResponse<List<CategoryResponse>> result = categoryService.getAllCategories();
//
// assertEquals("All categories retrieved successfully", result.getMessage());
// assertEquals(1, result.getData().size());
// assertEquals(mapped, result.getData().get(0));
// }
// }
//
// @Test
// void getCategoriesForUser_shouldReturnMappedList() {
// Long userId = 7L;
// Category c = Mockito.mock(Category.class);
// CategoryResponse mapped = CategoryResponse.builder().categoryId(33L).build();
//
// when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
// when(categoryRepository.findByUser_Id(userId)).thenReturn(List.of(c));
//
// try (MockedStatic<CategoryMapper> mapperMock = Mockito.mockStatic(CategoryMapper.class)) {
// mapperMock.when(() -> CategoryMapper.toResponse(c)).thenReturn(mapped);
//
// ApiResponse<List<CategoryResponse>> result = categoryService.getCategoriesForUser(userId);
//
// assertEquals("User categories retrieved successfully", result.getMessage());
// assertEquals(1, result.getData().size());
// assertEquals(mapped, result.getData().get(0));
// }
// }
//
// @Test
// void getCategoriesForUser_shouldThrowWhenUserNotFound() {
// when(userRepository.findById(3L)).thenReturn(Optional.empty());
//
// assertThrows(EntityNotFoundException.class, () -> categoryService.getCategoriesForUser(3L));
// }
//
// @Test
// void deleteCategory_shouldDeleteAndReturnOk() {
// Category category = Mockito.mock(Category.class);
//
// when(categoryRepository.findById(4L)).thenReturn(Optional.of(category));
//
// ApiResponse<Void> result = categoryService.deleteCategory(4L);
//
// assertEquals("Category deleted successfully", result.getMessage());
// assertNull(result.getData());
// verify(categoryRepository).delete(category);
// }
//
// @Test
// void deleteCategory_shouldThrowWhenNotFound() {
// when(categoryRepository.findById(77L)).thenReturn(Optional.empty());
//
// assertThrows(EntityNotFoundException.class, () -> categoryService.deleteCategory(77L));
// }
//
// @Test
// void createCategory_shouldReturnWrappedResponse() {
// Long userId = 5L;
// CategoryRequest req = new CategoryRequest();
// CategoryResponse res = CategoryResponse.builder().categoryId(10L).build();
// ApiResponse<CategoryResponse> api = ApiResponse.ok("ok", res);
//
// when(categoryServiceMock.createCategory(userId, req)).thenReturn(api);
//
// ResponseEntity<ApiResponse<CategoryResponse>> result = categoryController.createCategory(userId, req);
//
// assertEquals(api, result.getBody());
// }
//
// @Test
// void getAllCategories_shouldReturnWrappedResponse() {
// CategoryResponse mapped = CategoryResponse.builder().categoryId(20L).build();
// ApiResponse<List<CategoryResponse>> api = ApiResponse.ok("ok", List.of(mapped));
//
// when(categoryServiceMock.getAllCategories()).thenReturn(api);
//
// ResponseEntity<ApiResponse<List<CategoryResponse>>> result = categoryController.getAllCategories();
//
// assertEquals(api, result.getBody());
// }
//
// @Test
// void getCategoriesForUser_shouldReturnWrappedResponse() {
// Long userId = 7L;
// CategoryResponse mapped = CategoryResponse.builder().categoryId(33L).build();
// ApiResponse<List<CategoryResponse>> api = ApiResponse.ok("ok", List.of(mapped));
//
// when(categoryServiceMock.getCategoriesForUser(userId)).thenReturn(api);
//
// ResponseEntity<ApiResponse<List<CategoryResponse>>> result = categoryController.getCategoriesForUser(userId);
//
// assertEquals(api, result.getBody());
// }
//
// @Test
// void deleteCategory_shouldReturnWrappedResponse() {
// Long categoryId = 99L;
// ApiResponse<Void> api = ApiResponse.ok("deleted", null);
//
// when(categoryServiceMock.deleteCategory(categoryId)).thenReturn(api);
//
// ResponseEntity<ApiResponse<Void>> result = categoryController.deleteCategory(categoryId);
//
// assertEquals(api, result.getBody());
// }
//
// @Test
// void userMapper_toEntity_shouldMapFields() {
// UserRequest req = Mockito.mock(UserRequest.class);
//
// when(req.getUsername()).thenReturn("marcus");
// when(req.getEmail()).thenReturn("m@example.com");
// when(req.getPassword()).thenReturn("secret123");
//
// User result = UserMapper.toEntity(req);
//
// assertEquals("marcus", result.getUsername());
// assertEquals("m@example.com", result.getEmail());
// assertEquals("secret123", result.getPassword());
// }
//
// @Test
// void userMapper_toResponse_shouldMapAllFields() {
// User user = Mockito.mock(User.class);
//
// when(user.getId()).thenReturn(10L);
// when(user.getUsername()).thenReturn("marcus");
// when(user.getEmail()).thenReturn("m@example.com");
// when(user.getCreatedAt()).thenReturn(Instant.now());
// when(user.getUpdatedAt()).thenReturn(Instant.now());
// when(user.getRole()).thenReturn(Role.USER);
//
// UserResponse res = UserMapper.toResponse(user);
//
// assertEquals(10L, res.getId());
// assertEquals("marcus", res.getUsername());
// assertEquals("m@example.com", res.getEmail());
// assertNotNull(res.getCreatedAt());
// assertNotNull(res.getUpdatedAt());
// assertEquals(Role.USER, res.getRole());
// }
//
// @Test
// void userMapper_toResponse_shouldThrowWhenUserNull() {
// assertThrows(NullPointerException.class, () -> UserMapper.toResponse(null));
// }
//
// @Test
// void getAllUsers_shouldReturnMappedList() {
// User u = Mockito.mock(User.class);
// when(userRepository.findAll()).thenReturn(List.of(u));
//
// UserResponse mapped = UserResponse.builder().id(1L).build();
//
// try (MockedStatic<UserMapper> mapperMock = Mockito.mockStatic(UserMapper.class)) {
// mapperMock.when(() -> UserMapper.toResponse(u)).thenReturn(mapped);
//
// ApiResponse<List<UserResponse>> result = userService.getAllUsers();
//
// assertEquals("Users retrieved successfully", result.getMessage());
// assertNotNull(result.getData());
// assertEquals(1, result.getData().size());
// assertEquals(mapped, result.getData().get(0));
// }
// }
//
// @Test
// void getUserById_shouldReturnUser() {
// User u = Mockito.mock(User.class);
// when(userRepository.findById(10L)).thenReturn(Optional.of(u));
//
// UserResponse mapped = UserResponse.builder().id(10L).build();
//
// try (MockedStatic<UserMapper> mapperMock = Mockito.mockStatic(UserMapper.class)) {
// mapperMock.when(() -> UserMapper.toResponse(u)).thenReturn(mapped);
//
// ApiResponse<UserResponse> result = userService.getUserById(10L);
//
// assertEquals("User retrieved successfully", result.getMessage());
// assertEquals(mapped, result.getData());
// }
// }
//
// @Test
// void getUserById_shouldThrowWhenNotFound() {
// when(userRepository.findById(10L)).thenReturn(Optional.empty());
//
// assertThrows(EntityNotFoundException.class, () -> userService.getUserById(10L));
// }
//
// @Test
// void createUser_shouldCreateUserAndDefaultAccount() {
// UserRequest req = Mockito.mock(UserRequest.class);
// when(req.getUsername()).thenReturn("marcus");
// when(req.getEmail()).thenReturn("m@example.com");
// when(req.getPassword()).thenReturn("secret");
//
// when(userRepository.existsByUsername("marcus")).thenReturn(false);
// when(userRepository.existsByEmail("m@example.com")).thenReturn(false);
//
// User entity = Mockito.mock(User.class);
// User saved = Mockito.mock(User.class);
// UserResponse mapped = UserResponse.builder().id(1L).build();
//
// when(passwordEncoder.encode("secret")).thenReturn("encoded-secret");
// when(userRepository.save(entity)).thenReturn(saved);
//
// try (MockedStatic<UserMapper> mapperMock = Mockito.mockStatic(UserMapper.class)) {
// mapperMock.when(() -> UserMapper.toEntity(req)).thenReturn(entity);
// mapperMock.when(() -> UserMapper.toResponse(saved)).thenReturn(mapped);
//
// ApiResponse<UserResponse> result = userService.createUser(req);
//
// assertEquals("User created successfully", result.getMessage());
// assertEquals(mapped, result.getData());
//
// verify(entity).setPassword("encoded-secret");
// verify(userRepository).save(entity);
// verify(accountRepository).save(any(Account.class));
// }
// }
//
// @Test
// void createUser_shouldThrowWhenUsernameExists() {
// UserRequest req = Mockito.mock(UserRequest.class);
// when(req.getUsername()).thenReturn("marcus");
//
// when(userRepository.existsByUsername("marcus")).thenReturn(true);
//
// assertThrows(DataIntegrityViolationException.class, () -> userService.createUser(req));
// }
//
// @Test
// void createUser_shouldThrowWhenEmailExists() {
// UserRequest req = Mockito.mock(UserRequest.class);
// when(req.getUsername()).thenReturn("marcus");
// when(req.getEmail()).thenReturn("m@example.com");
//
// when(userRepository.existsByUsername("marcus")).thenReturn(false);
// when(userRepository.existsByEmail("m@example.com")).thenReturn(true);
//
// assertThrows(DataIntegrityViolationException.class, () -> userService.createUser(req));
// }
//
// @Test
// void updateUserById_shouldUpdateAndReturnUser() {
// Long id = 5L;
// User existing = Mockito.mock(User.class, RETURNS_DEEP_STUBS);
// User updated = Mockito.mock(User.class);
// User saved = Mockito.mock(User.class);
// UserResponse mapped = UserResponse.builder().id(id).build();
//
// UserRequest req = Mockito.mock(UserRequest.class);
// when(req.getUsername()).thenReturn("newuser");
// when(req.getEmail()).thenReturn("new@example.com");
// when(req.getPassword()).thenReturn("newpass");
//
// when(userRepository.findById(id)).thenReturn(Optional.of(existing));
// when(passwordEncoder.encode("newpass")).thenReturn("encoded-new");
// when(existing.toBuilder().username("newuser").email("new@example.com").password("encoded-new").build())
// .thenReturn(updated);
// when(userRepository.save(updated)).thenReturn(saved);
//
// try (MockedStatic<UserMapper> mapperMock = Mockito.mockStatic(UserMapper.class)) {
// mapperMock.when(() -> UserMapper.toResponse(saved)).thenReturn(mapped);
//
// ApiResponse<UserResponse> result = userService.updateUserById(id, req);
//
// assertEquals("User updated successfully", result.getMessage());
// assertEquals(mapped, result.getData());
// verify(userRepository).save(updated);
// }
// }
//
// @Test
// void updateUserById_shouldThrowWhenNotFound() {
// UserRequest req = Mockito.mock(UserRequest.class);
// when(userRepository.findById(99L)).thenReturn(Optional.empty());
//
// assertThrows(EntityNotFoundException.class, () -> userService.updateUserById(99L, req));
// }
//
// @Test
// void deleteUserById_shouldDeleteAndReturnUser() {
// Long id = 8L;
// User user = Mockito.mock(User.class);
// UserResponse mapped = UserResponse.builder().id(id).build();
//
// when(userRepository.findById(id)).thenReturn(Optional.of(user));
//
// try (MockedStatic<UserMapper> mapperMock = Mockito.mockStatic(UserMapper.class)) {
// mapperMock.when(() -> UserMapper.toResponse(user)).thenReturn(mapped);
//
// ApiResponse<UserResponse> result = userService.deleteUserById(id);
//
// assertEquals("User deleted successfully", result.getMessage());
// assertEquals(mapped, result.getData());
// verify(userRepository).deleteById(id);
// }
// }
//
// @Test
// void deleteUserById_shouldThrowWhenNotFound() {
// when(userRepository.findById(11L)).thenReturn(Optional.empty());
//
// assertThrows(EntityNotFoundException.class, () -> userService.deleteUserById(11L));
// }
//
// @Test
// void loginUser_shouldSucceedAndNotCreateCheckingIfExists() {
// LoginRequest req = new LoginRequest();
// req.setUsername("marcus");
// req.setPassword("secret");
//
// User u = Mockito.mock(User.class);
// when(userRepository.findByUsername("marcus")).thenReturn(Optional.of(u));
// when(u.getPassword()).thenReturn("hashed");
// when(passwordEncoder.matches("secret", "hashed")).thenReturn(true);
// when(u.getId()).thenReturn(1L);
//
// Account checking = Mockito.mock(Account.class);
// when(checking.getAccountType()).thenReturn(AccountType.CHECKING);
// when(accountRepository.findByUserId(1L)).thenReturn(List.of(checking));
//
// UserResponse mapped = UserResponse.builder().id(1L).build();
//
// try (MockedStatic<UserMapper> mapperMock = Mockito.mockStatic(UserMapper.class)) {
// mapperMock.when(() -> UserMapper.toResponse(u)).thenReturn(mapped);
//
// ApiResponse<UserResponse> result = userService.loginUser(req);
//
// assertEquals("Login successful", result.getMessage());
// assertEquals(mapped, result.getData());
// verify(accountRepository, times(0)).save(any(Account.class));
// }
// }
//
// @Test
// void loginUser_shouldCreateCheckingIfMissing() {
// LoginRequest req = new LoginRequest();
// req.setUsername("marcus");
// req.setPassword("secret");
//
// User u = Mockito.mock(User.class);
// when(userRepository.findByUsername("marcus")).thenReturn(Optional.of(u));
// when(u.getPassword()).thenReturn("hashed");
// when(passwordEncoder.matches("secret", "hashed")).thenReturn(true);
// when(u.getId()).thenReturn(2L);
//
// when(accountRepository.findByUserId(2L)).thenReturn(List.of());
//
// UserResponse mapped = UserResponse.builder().id(2L).build();
//
// try (MockedStatic<UserMapper> mapperMock = Mockito.mockStatic(UserMapper.class)) {
// mapperMock.when(() -> UserMapper.toResponse(u)).thenReturn(mapped);
//
// ApiResponse<UserResponse> result = userService.loginUser(req);
//
// assertEquals("Login successful", result.getMessage());
// assertEquals(mapped, result.getData());
// verify(accountRepository).save(any(Account.class));
// }
// }
//
// @Test
// void loginUser_shouldThrowWhenUserNotFound() {
// LoginRequest req = new LoginRequest();
// req.setUsername("nope");
// req.setPassword("x");
//
// when(userRepository.findByUsername("nope")).thenReturn(Optional.empty());
//
// assertThrows(InvalidCredentialsException.class, () -> userService.loginUser(req));
// }
//
// @Test
// void loginUser_shouldThrowWhenPasswordMismatch() {
// LoginRequest req = new LoginRequest();
// req.setUsername("marcus");
// req.setPassword("wrong");
//
// User u = Mockito.mock(User.class);
// when(userRepository.findByUsername("marcus")).thenReturn(Optional.of(u));
// when(u.getPassword()).thenReturn("hashed");
// when(passwordEncoder.matches("wrong", "hashed")).thenReturn(false);
//
// assertThrows(InvalidCredentialsException.class, () -> userService.loginUser(req));
// }
//
// @Test
// void patchUserById_shouldUpdateProvidedFields() {
// Long id = 15L;
// User existing = Mockito.mock(User.class);
// UserPatchRequest patch = Mockito.mock(UserPatchRequest.class);
//
// when(userRepository.findById(id)).thenReturn(Optional.of(existing));
// when(patch.getUsername()).thenReturn("newname");
// when(patch.getEmail()).thenReturn("new@example.com");
// when(patch.getPassword()).thenReturn("newpass");
// when(patch.getRole()).thenReturn(Role.ADMIN);
// when(passwordEncoder.encode("newpass")).thenReturn("encoded-pass");
//
// UserResponse mapped = UserResponse.builder().id(id).build();
//
// try (MockedStatic<UserMapper> mapperMock = Mockito.mockStatic(UserMapper.class)) {
// mapperMock.when(() -> UserMapper.toResponse(existing)).thenReturn(mapped);
//
// ApiResponse<UserResponse> result = userService.patchUserById(id, patch);
//
// assertEquals("User updated succesfully", result.getMessage());
// assertEquals(mapped, result.getData());
//
// verify(existing).setUsername("newname");
// verify(existing).setEmail("new@example.com");
// verify(existing).setPassword("encoded-pass");
// verify(existing).setRole(Role.ADMIN);
// }
// }
//
// @Test
// void patchUserById_shouldThrowWhenNotFound() {
// UserPatchRequest patch = new UserPatchRequest();
// when(userRepository.findById(20L)).thenReturn(Optional.empty());
//
// assertThrows(EntityNotFoundException.class, () -> userService.patchUserById(20L, patch));
// }
//
// @Test
// void getUserCount_shouldReturnCount() {
// when(userRepository.count()).thenReturn(42L);
//
// long result = userService.getUserCount();
//
// assertEquals(42L, result);
// }
//
// @Test
// void getUserCountByRole_shouldReturnCount() {
// when(userRepository.countByRole(Role.USER)).thenReturn(5L);
//
// long result = userService.getUserCountByRole(Role.USER);
//
// assertEquals(5L, result);
// }
//
// @Test
// void loginUser_shouldCreateCheckingWhenUserOnlyHasNonCheckingAccounts() {
// LoginRequest req = new LoginRequest();
// req.setUsername("marcus");
// req.setPassword("secret");
//
// User u = Mockito.mock(User.class);
// when(userRepository.findByUsername("marcus")).thenReturn(Optional.of(u));
// when(u.getPassword()).thenReturn("hashed");
// when(passwordEncoder.matches("secret", "hashed")).thenReturn(true);
// when(u.getId()).thenReturn(3L);
//
// Account savings = Mockito.mock(Account.class);
// when(savings.getAccountType()).thenReturn(AccountType.SAVINGS);
//
// when(accountRepository.findByUserId(3L)).thenReturn(List.of(savings));
//
// UserResponse mapped = UserResponse.builder().id(3L).build();
//
// try (MockedStatic<UserMapper> mapperMock = Mockito.mockStatic(UserMapper.class)) {
// mapperMock.when(() -> UserMapper.toResponse(u)).thenReturn(mapped);
//
// ApiResponse<UserResponse> result = userService.loginUser(req);
//
// assertEquals("Login successful", result.getMessage());
// assertEquals(mapped, result.getData());
//
// verify(accountRepository).save(any(Account.class));
// }
// }
//
// @Test
// void patchUserById_shouldNotUpdateWhenFieldsNullOrBlank() {
// Long id = 30L;
// User existing = Mockito.mock(User.class);
// UserPatchRequest patch = Mockito.mock(UserPatchRequest.class);
//
// when(userRepository.findById(id)).thenReturn(Optional.of(existing));
//
// when(patch.getUsername()).thenReturn(null);
// when(patch.getEmail()).thenReturn(" ");
// when(patch.getPassword()).thenReturn(null);
// when(patch.getRole()).thenReturn(null);
//
// UserResponse mapped = UserResponse.builder().id(id).build();
//
// try (MockedStatic<UserMapper> mapperMock = Mockito.mockStatic(UserMapper.class)) {
// mapperMock.when(() -> UserMapper.toResponse(existing)).thenReturn(mapped);
//
// ApiResponse<UserResponse> result = userService.patchUserById(id, patch);
//
// assertEquals("User updated succesfully", result.getMessage());
// assertEquals(mapped, result.getData());
// }
//
// verify(existing, times(0)).setUsername(anyString());
// verify(existing, times(0)).setEmail(anyString());
// verify(existing, times(0)).setPassword(anyString());
// verify(existing, times(0)).setRole(any(Role.class));
// verify(passwordEncoder, times(0)).encode(anyString());
// }
//
// @Test
// void patchUserById_shouldNotUpdateWhenFieldsAreBlankStrings() {
// Long id = 31L;
// User existing = Mockito.mock(User.class);
// UserPatchRequest patch = Mockito.mock(UserPatchRequest.class);
//
// when(userRepository.findById(id)).thenReturn(Optional.of(existing));
//
// when(patch.getUsername()).thenReturn(" ");
// when(patch.getEmail()).thenReturn(" ");
// when(patch.getPassword()).thenReturn(" ");
// when(patch.getRole()).thenReturn(null);
//
// UserResponse mapped = UserResponse.builder().id(id).build();
//
// try (MockedStatic<UserMapper> mapperMock = Mockito.mockStatic(UserMapper.class)) {
// mapperMock.when(() -> UserMapper.toResponse(existing)).thenReturn(mapped);
//
// ApiResponse<UserResponse> result = userService.patchUserById(id, patch);
//
// assertEquals("User updated succesfully", result.getMessage());
// assertEquals(mapped, result.getData());
// }
//
// verify(existing, times(0)).setUsername(anyString());
// verify(existing, times(0)).setEmail(anyString());
// verify(existing, times(0)).setPassword(anyString());
// verify(existing, times(0)).setRole(any(Role.class));
// verify(passwordEncoder, times(0)).encode(anyString());
// }
//
// @Test
// void patchUserById_shouldUpdateEmailWhenNonNullAndNonBlank() {
// Long id = 50L;
// User existing = Mockito.mock(User.class);
// UserPatchRequest patch = Mockito.mock(UserPatchRequest.class);
//
// when(userRepository.findById(id)).thenReturn(Optional.of(existing));
//
// when(patch.getUsername()).thenReturn(null);
// when(patch.getEmail()).thenReturn("new@email.com");
// when(patch.getPassword()).thenReturn(null);
// when(patch.getRole()).thenReturn(null);
//
// UserResponse mapped = UserResponse.builder().id(id).build();
//
// try (MockedStatic<UserMapper> mapperMock = Mockito.mockStatic(UserMapper.class)) {
// mapperMock.when(() -> UserMapper.toResponse(existing)).thenReturn(mapped);
//
// ApiResponse<UserResponse> result = userService.patchUserById(id, patch);
//
// assertEquals("User updated succesfully", result.getMessage());
// assertEquals(mapped, result.getData());
// }
//
// verify(existing).setEmail("new@email.com");
// }
//
// @Test
// void getAllUsers_shouldReturnWrappedResponse() {
// UserResponse u = UserResponse.builder().id(1L).build();
// ApiResponse<List<UserResponse>> api = ApiResponse.ok("ok", List.of(u));
//
// when(userServiceMock.getAllUsers()).thenReturn(api);
//
// ResponseEntity<ApiResponse<List<UserResponse>>> result = userController.getAllUsers();
//
// assertEquals(api, result.getBody());
// }
//
// @Test
// void getUserById_shouldReturnWrappedResponse() {
// Long id = 10L;
// UserResponse u = UserResponse.builder().id(id).build();
// ApiResponse<UserResponse> api = ApiResponse.ok("ok", u);
//
// when(userServiceMock.getUserById(id)).thenReturn(api);
//
// ResponseEntity<ApiResponse<UserResponse>> result = userController.getUserById(id);
//
// assertEquals(api, result.getBody());
// }
//
// @Test
// void createUser_shouldReturnWrappedResponse() {
// UserRequest req = new UserRequest();
// UserResponse u = UserResponse.builder().id(2L).build();
// ApiResponse<UserResponse> api = ApiResponse.created("created", u);
//
// when(userServiceMock.createUser(req)).thenReturn(api);
//
// ResponseEntity<ApiResponse<UserResponse>> result = userController.createUser(req);
//
// assertEquals(api, result.getBody());
// }
//
// @Test
// void updateUserById_shouldReturnWrappedResponse() {
// Long id = 3L;
// UserRequest req = new UserRequest();
// UserResponse u = UserResponse.builder().id(id).build();
// ApiResponse<UserResponse> api = ApiResponse.ok("updated", u);
//
// when(userServiceMock.updateUserById(id, req)).thenReturn(api);
//
// ResponseEntity<ApiResponse<UserResponse>> result = userController.updateUserById(id, req);
//
// assertEquals(api, result.getBody());
// }
//
// @Test
// void deleteUserById_shouldReturnWrappedResponse() {
// Long id = 4L;
// UserResponse u = UserResponse.builder().id(id).build();
// ApiResponse<UserResponse> api = ApiResponse.ok("deleted", u);
//
// when(userServiceMock.deleteUserById(id)).thenReturn(api);
//
// ResponseEntity<ApiResponse<UserResponse>> result = userController.deleteUserById(id);
//
// assertEquals(api, result.getBody());
// }
//
// @Test
// void loginUser_shouldReturnWrappedResponse() {
// LoginRequest login = new LoginRequest();
// UserResponse u = UserResponse.builder().id(5L).build();
// ApiResponse<UserResponse> api = ApiResponse.ok("login ok", u);
//
// when(userServiceMock.loginUser(login)).thenReturn(api);
//
// ResponseEntity<ApiResponse<UserResponse>> result = userController.loginUser(login);
//
// assertEquals(api, result.getBody());
// }
//
// @Test
// void patchUserById_shouldReturnWrappedResponse() {
// Long id = 6L;
// UserPatchRequest patch = new UserPatchRequest();
// UserResponse u = UserResponse.builder().id(id).build();
// ApiResponse<UserResponse> api = ApiResponse.ok("patched", u);
//
// when(userServiceMock.patchUserById(id, patch)).thenReturn(api);
//
// ResponseEntity<ApiResponse<UserResponse>> result = userController.patchUserById(id, patch);
//
// assertEquals(api, result.getBody());
// }
//
// @Test
// void customUserDetailsService_shouldLoadUserByUsername() {
// User userEntity = Mockito.mock(User.class);
//
// when(userRepository.findByUsername("marcus")).thenReturn(Optional.of(userEntity));
// when(userEntity.getUsername()).thenReturn("marcus");
// when(userEntity.getPassword()).thenReturn("encoded-pass");
// when(userEntity.getRole()).thenReturn(Role.USER);
//
// UserDetails details = customUserDetailsService.loadUserByUsername("marcus");
//
// assertEquals("marcus", details.getUsername());
// assertEquals("encoded-pass", details.getPassword());
// assertTrue(details.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
// }
//
// @Test
// void customUserDetailsService_shouldThrowWhenUserNotFound() {
// when(userRepository.findByUsername("missing")).thenReturn(Optional.empty());
//
// assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername("missing"));
// }
//
// @Test
// void newsMapper_toEntity_shouldMapAllFields() {
// NewsDto dto = Mockito.mock(NewsDto.class);
//
// when(dto.getId()).thenReturn(10L);
// when(dto.getCategory()).thenReturn("general");
// when(dto.getDatetime()).thenReturn(1_700_000_000L);
// when(dto.getHeadline()).thenReturn("Big headline");
// when(dto.getImage()).thenReturn("http://image");
// when(dto.getRelated()).thenReturn("AAPL,MSFT");
// when(dto.getSource()).thenReturn("Finnhub");
// when(dto.getSummary()).thenReturn("Some summary");
// when(dto.getUrl()).thenReturn("http://article");
//
// News entity = NewsMapper.toEntity(dto);
//
// assertEquals(10L, entity.getId());
// assertEquals("general", entity.getCategory());
// assertEquals(Instant.ofEpochSecond(1_700_000_000L), entity.getDatetime());
// assertEquals("Big headline", entity.getHeadline());
// assertEquals("http://image", entity.getImage());
// assertEquals("AAPL,MSFT", entity.getRelated());
// assertEquals("Finnhub", entity.getSource());
// assertEquals("Some summary", entity.getSummary());
// assertEquals("http://article", entity.getUrl());
// }
//
// @Test
// void newsMapper_toResponse_shouldMapAllFields() {
// News news = Mockito.mock(News.class);
// Instant ts = Instant.ofEpochSecond(1_700_000_000L);
//
// when(news.getId()).thenReturn(20L);
// when(news.getCategory()).thenReturn("stocks");
// when(news.getDatetime()).thenReturn(ts);
// when(news.getHeadline()).thenReturn("Market moves");
// when(news.getImage()).thenReturn("http://img2");
// when(news.getRelated()).thenReturn("TSLA");
// when(news.getSource()).thenReturn("NewsAPI");
// when(news.getSummary()).thenReturn("Market summary");
// when(news.getUrl()).thenReturn("http://news");
//
// NewsResponse res = NewsMapper.toResponse(news);
//
// assertEquals(20L, res.getId());
// assertEquals("stocks", res.getCategory());
// assertEquals(ts, res.getDatetime());
// assertEquals("Market moves", res.getHeadline());
// assertEquals("http://img2", res.getImage());
// assertEquals("TSLA", res.getRelated());
// assertEquals("NewsAPI", res.getSource());
// assertEquals("Market summary", res.getSummary());
// assertEquals("http://news", res.getUrl());
// }
//
// @Test
// void getAllNews_shouldReturnMappedList() {
// int page = 0;
// int size = 10;
//
// News news = Mockito.mock(News.class);
// Page<News> newsPage = new PageImpl<>(List.of(news));
// when(newsRepository.findAllPrioritized(any(Pageable.class))).thenReturn(newsPage);
//
// NewsResponse mapped = NewsResponse.builder().id(1L).build();
//
// try (MockedStatic<NewsMapper> mapperMock = Mockito.mockStatic(NewsMapper.class)) {
// mapperMock.when(() -> NewsMapper.toResponse(news)).thenReturn(mapped);
//
// ApiResponse<List<NewsResponse>> result = newsService.getAllNews(page, size);
//
// assertEquals("News retrieved successfully", result.getMessage());
// assertNotNull(result.getData());
// assertEquals(1, result.getData().size());
// assertEquals(mapped, result.getData().get(0));
// }
// }
//
// @Test
// void getNewsBySymbol_shouldReturnMappedList() {
// String symbol = "AAPL";
// int page = 0;
// int size = 5;
//
// News news = Mockito.mock(News.class);
// Page<News> newsPage = new PageImpl<>(List.of(news));
// when(newsRepository.findByRelatedIgnoreCase(eq(symbol), any(Pageable.class))).thenReturn(newsPage);
//
// NewsResponse mapped = NewsResponse.builder().id(2L).build();
//
// try (MockedStatic<NewsMapper> mapperMock = Mockito.mockStatic(NewsMapper.class)) {
// mapperMock.when(() -> NewsMapper.toResponse(news)).thenReturn(mapped);
//
// ApiResponse<List<NewsResponse>> result = newsService.getNewsBySymbol(symbol, page, size);
//
// assertEquals("News retrieved successfully", result.getMessage());
// assertEquals(1, result.getData().size());
// assertEquals(mapped, result.getData().get(0));
// }
// }
//
// @Test
// void getNewsBySymbol_shouldThrowWhenEmpty() {
// String symbol = "MSFT";
// int page = 0;
// int size = 5;
//
// Page<News> emptyPage = new PageImpl<>(List.of());
// when(newsRepository.findByRelatedIgnoreCase(eq(symbol), any(Pageable.class))).thenReturn(emptyPage);
//
// assertThrows(EntityNotFoundException.class, () -> newsService.getNewsBySymbol(symbol, page, size));
// }
//
// @Test
// void getNewsBySource_shouldReturnMappedList() {
// String source = "Finnhub";
// int page = 1;
// int size = 3;
//
// News news = Mockito.mock(News.class);
// Page<News> newsPage = new PageImpl<>(List.of(news));
// when(newsRepository.findBySourceIgnoreCase(eq(source), any(Pageable.class))).thenReturn(newsPage);
//
// NewsResponse mapped = NewsResponse.builder().id(3L).build();
//
// try (MockedStatic<NewsMapper> mapperMock = Mockito.mockStatic(NewsMapper.class)) {
// mapperMock.when(() -> NewsMapper.toResponse(news)).thenReturn(mapped);
//
// ApiResponse<List<NewsResponse>> result = newsService.getNewsBySource(source, page, size);
//
// assertEquals("News retrieved successfully", result.getMessage());
// assertEquals(1, result.getData().size());
// assertEquals(mapped, result.getData().get(0));
// }
// }
//
// @Test
// void getNewsBySource_shouldThrowWhenEmpty() {
// String source = "UnknownSource";
// int page = 0;
// int size = 3;
//
// Page<News> emptyPage = new PageImpl<>(List.of());
// when(newsRepository.findBySourceIgnoreCase(eq(source), any(Pageable.class))).thenReturn(emptyPage);
//
// assertThrows(EntityNotFoundException.class, () -> newsService.getNewsBySource(source, page, size));
// }
//
// @Test
// void getNewsByCategory_shouldReturnMappedList() {
// String category = "technology";
// int page = 0;
// int size = 4;
//
// News news = Mockito.mock(News.class);
// Page<News> newsPage = new PageImpl<>(List.of(news));
// when(newsRepository.findByCategoryIgnoreCase(eq(category), any(Pageable.class))).thenReturn(newsPage);
//
// NewsResponse mapped = NewsResponse.builder().id(4L).build();
//
// try (MockedStatic<NewsMapper> mapperMock = Mockito.mockStatic(NewsMapper.class)) {
// mapperMock.when(() -> NewsMapper.toResponse(news)).thenReturn(mapped);
//
// ApiResponse<List<NewsResponse>> result = newsService.getNewsByCategory(category, page, size);
//
// assertEquals("News retrieved successfully", result.getMessage());
// assertEquals(1, result.getData().size());
// assertEquals(mapped, result.getData().get(0));
// }
// }
//
// @Test
// void getNewsByCategory_shouldThrowWhenEmpty() {
// String category = "crypto";
// int page = 0;
// int size = 4;
//
// Page<News> emptyPage = new PageImpl<>(List.of());
// when(newsRepository.findByCategoryIgnoreCase(eq(category), any(Pageable.class))).thenReturn(emptyPage);
//
// assertThrows(EntityNotFoundException.class, () -> newsService.getNewsByCategory(category, page, size));
// }
//
// @Test
// void fetchMarketNews_shouldFetchAndSaveNewItems() {
// ReflectionTestUtils.setField(marketNewsScheduler, "categories", List.of("general"));
//
// when(newsRepository.findAllIds()).thenReturn(List.of(1L, 2L));
//
// NewsDto dto1 = Mockito.mock(NewsDto.class);
// NewsDto dto2 = Mockito.mock(NewsDto.class);
//
// when(dto1.getId()).thenReturn(3L);
// when(dto2.getId()).thenReturn(4L);
// when(dto1.getDatetime()).thenReturn(1_700_000_000L);
// when(dto2.getDatetime()).thenReturn(1_700_000_001L);
//
// String url = "http://api/news?category=general&token=KEY";
// when(restTemplate.getForObject(eq(url), eq(NewsDto[].class))).thenReturn(new NewsDto[] { dto1, dto2 });
//
// assertDoesNotThrow(() -> marketNewsScheduler.fetchMarketNews());
//
// ArgumentCaptor<List<News>> captor = ArgumentCaptor.forClass(List.class);
// verify(newsRepository).saveAll(captor.capture());
// assertEquals(2, captor.getValue().size());
// }
//
// @Test
// void fetchMarketNews_shouldNotSaveWhenAllNewsAlreadyExist() {
// ReflectionTestUtils.setField(marketNewsScheduler, "categories", List.of("general"));
//
// when(newsRepository.findAllIds()).thenReturn(List.of(10L));
//
// NewsDto dto = Mockito.mock(NewsDto.class);
// when(dto.getId()).thenReturn(10L);
// when(dto.getDatetime()).thenReturn(1_700_000_000L);
//
// String url = "http://api/news?category=general&token=KEY";
// when(restTemplate.getForObject(eq(url), eq(NewsDto[].class))).thenReturn(new NewsDto[] { dto });
//
// assertDoesNotThrow(() -> marketNewsScheduler.fetchMarketNews());
//
// verify(newsRepository, Mockito.never()).saveAll(Mockito.anyList());
// }
//
// @Test
// void fetchMarketNews_shouldHandleNullResponseGracefully() {
// ReflectionTestUtils.setField(marketNewsScheduler, "categories", List.of("general"));
//
// when(newsRepository.findAllIds()).thenReturn(List.of());
//
// String url = "http://api/news?category=general&token=KEY";
// when(restTemplate.getForObject(eq(url), eq(NewsDto[].class))).thenReturn(null);
//
// assertDoesNotThrow(() -> marketNewsScheduler.fetchMarketNews());
//
// verify(newsRepository, Mockito.never()).saveAll(Mockito.anyList());
// }
//
// @Test
// void fetchMarketNews_shouldHandleRestClientException() {
// ReflectionTestUtils.setField(marketNewsScheduler, "categories", List.of("general"));
//
// when(newsRepository.findAllIds()).thenReturn(List.of());
//
// String url = "http://api/news?category=general&token=KEY";
// when(restTemplate.getForObject(eq(url), eq(NewsDto[].class))).thenThrow(new RestClientException("fail"));
//
// assertDoesNotThrow(() -> marketNewsScheduler.fetchMarketNews());
//
// verify(newsRepository, Mockito.never()).saveAll(Mockito.anyList());
// }
//
// @Test
// void fetchMarketNews_shouldHandleEmptyArrayGracefully() {
// ReflectionTestUtils.setField(marketNewsScheduler, "categories", List.of("general"));
//
// when(newsRepository.findAllIds()).thenReturn(List.of());
//
// String url = "http://api/news?category=general&token=KEY";
// when(restTemplate.getForObject(eq(url), eq(NewsDto[].class))).thenReturn(new NewsDto[] {}); // EMPTY ARRAY
//
// assertDoesNotThrow(() -> marketNewsScheduler.fetchMarketNews());
//
// verify(newsRepository, Mockito.never()).saveAll(Mockito.anyList());
// }
//
// @Test
// void fetchCompanyNews_shouldFetchAndSaveNewItems() {
// when(newsRepository.findAllIds()).thenReturn(List.of(1L));
// when(symbolRepository.findAllSymbols()).thenReturn(List.of("AAPL"));
// when(rateLimiter.acquire()).thenReturn(1.0);
//
// NewsDto dto1 = Mockito.mock(NewsDto.class);
// NewsDto dto2 = Mockito.mock(NewsDto.class);
//
// when(dto1.getId()).thenReturn(2L);
// when(dto2.getId()).thenReturn(3L);
// when(dto1.getDatetime()).thenReturn(1_700_000_000L);
// when(dto2.getDatetime()).thenReturn(1_700_000_001L);
//
// when(restTemplate.getForObject(Mockito.anyString(), eq(NewsDto[].class)))
// .thenReturn(new NewsDto[] { dto1, dto2 });
//
// try (MockedStatic<NewsMapper> mapperMock = Mockito.mockStatic(NewsMapper.class)) {
// News news1 = News.builder().id(2L).build();
// News news2 = News.builder().id(3L).build();
//
// mapperMock.when(() -> NewsMapper.toEntity(dto1)).thenReturn(news1);
// mapperMock.when(() -> NewsMapper.toEntity(dto2)).thenReturn(news2);
//
// assertDoesNotThrow(() -> companyNewsScheduler.fetchCompanyNews());
//
// ArgumentCaptor<List<News>> captor = ArgumentCaptor.forClass(List.class);
// verify(newsRepository).saveAll(captor.capture());
// assertEquals(2, captor.getValue().size());
// }
// }
//
// @Test
// void fetchCompanyNews_shouldHandleRestClientException() {
// when(newsRepository.findAllIds()).thenReturn(List.of());
// when(symbolRepository.findAllSymbols()).thenReturn(List.of("AAPL"));
// when(rateLimiter.acquire()).thenReturn(1.0);
//
// when(restTemplate.getForObject(Mockito.anyString(), eq(NewsDto[].class)))
// .thenThrow(new RestClientException("fail"));
//
// assertDoesNotThrow(() -> companyNewsScheduler.fetchCompanyNews());
//
// verify(newsRepository, Mockito.never()).saveAll(Mockito.anyList());
// }
//
// @Test
// void fetchCompanyNews_shouldHandleEmptyArrayGracefully() {
// when(newsRepository.findAllIds()).thenReturn(List.of());
// when(symbolRepository.findAllSymbols()).thenReturn(List.of("AAPL"));
// when(rateLimiter.acquire()).thenReturn(1.0);
//
// when(restTemplate.getForObject(Mockito.anyString(), eq(NewsDto[].class))).thenReturn(new NewsDto[] {});
//
// assertDoesNotThrow(() -> companyNewsScheduler.fetchCompanyNews());
//
// verify(newsRepository, Mockito.never()).saveAll(Mockito.anyList());
// }
//
// @Test
// void news_getAllNews_shouldReturnWrappedResponse() {
// ApiResponse<List<NewsResponse>> response = ApiResponse.ok("ok", List.of());
// when(newsServiceMock.getAllNews(0, 100)).thenReturn(response);
//
// ResponseEntity<ApiResponse<List<NewsResponse>>> result = newsController.getAllNews(0, 100);
//
// assertEquals(response, result.getBody());
// }
//
// @Test
// void news_getNewsBySymbol_shouldReturnWrappedResponse() {
// ApiResponse<List<NewsResponse>> response = ApiResponse.ok("ok", List.of());
// when(newsServiceMock.getNewsBySymbol("AAPL", 1, 50)).thenReturn(response);
//
// ResponseEntity<ApiResponse<List<NewsResponse>>> result = newsController.getNewsBySymbol("AAPL", 1, 50);
//
// assertEquals(response, result.getBody());
// }
//
// @Test
// void news_getNewsBySource_shouldReturnWrappedResponse() {
// ApiResponse<List<NewsResponse>> response = ApiResponse.ok("ok", List.of());
// when(newsServiceMock.getNewsBySource("Finnhub", 2, 25)).thenReturn(response);
//
// ResponseEntity<ApiResponse<List<NewsResponse>>> result = newsController.getNewsBySource("Finnhub", 2, 25);
//
// assertEquals(response, result.getBody());
// }
//
// @Test
// void news_getNewsByCategory_shouldReturnWrappedResponse() {
// ApiResponse<List<NewsResponse>> response = ApiResponse.ok("ok", List.of());
// when(newsServiceMock.getNewsByCategory("technology", 0, 10)).thenReturn(response);
//
// ResponseEntity<ApiResponse<List<NewsResponse>>> result = newsController.getNewsByCategory("technology", 0, 10);
//
// assertEquals(response, result.getBody());
// }
//
// }
