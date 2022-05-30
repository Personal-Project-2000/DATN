package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;
import static com.personal_game.datn.Backup.Constant.token_client;
import static com.personal_game.datn.ultilities.ConvertMoney.intConvertMoney;
import static com.personal_game.datn.ultilities.RangeTime.getBetweenDayToCurrent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.personal_game.datn.Adapter.BodyCostumeAdapter;
import com.personal_game.datn.Adapter.ColorAdapter;
import com.personal_game.datn.Adapter.CostumeAdapter;
import com.personal_game.datn.Adapter.CostumeImgAdapter;
import com.personal_game.datn.Adapter.SizeAdapter;
import com.personal_game.datn.Adapter.StyleAdapter;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Dialog.SizeGuideDialog;
import com.personal_game.datn.Models.Body;
import com.personal_game.datn.Models.ColorObject;
import com.personal_game.datn.Models.Costume;
import com.personal_game.datn.Models.PersonalStyle;
import com.personal_game.datn.Models.Picture;
import com.personal_game.datn.Models.Promotion;
import com.personal_game.datn.Models.Size;
import com.personal_game.datn.R;
import com.personal_game.datn.Request.Request_AddCart;
import com.personal_game.datn.Response.CostumeHome;
import com.personal_game.datn.Response.Message;
import com.personal_game.datn.Response.Message_Costume;
import com.personal_game.datn.databinding.ActivityCostumeBinding;
import com.personal_game.datn.ultilities.RangeTime;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CostumeActivity extends AppCompatActivity {
    private ActivityCostumeBinding binding;
    private CostumeImgAdapter costumeImgAdapter;
    private CostumeAdapter suitableOutfit;
    private CostumeAdapter costumeAdapter;
    private SizeAdapter sizeAdapter;
    private ColorAdapter colorAdapter;
    private StyleAdapter styleAdapter;
    private BodyCostumeAdapter bodyAdapter;

    private Shared_Preferences shared_preferences;
    private String costumeId = "";
    private String costumeName = "";
    private String costumeStyleId = "";
    private List<Picture> pictures;
    private List<PersonalStyle> personalStyles;
    private List<Body> bodyList;
    private List<CostumeHome> relatedCostumes = new ArrayList<>();
    private List<Picture> newPictures;
    private List<CostumeHome> suitableOutfitCostumes = new ArrayList<>();
    private List<Size> sizeList = new ArrayList<>();
    private List<ColorObject> colorList = new ArrayList<>();
    private boolean isFavorite = false;
    private int preColor = -1;
    private int preSize = -1;
    private int preIndexImg = 0;
    private CountDownTimer countDownTimer;
    private Costume costume;

    //kiểm tra đã bấm thêm chưa
    private boolean isAdd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCostumeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        shared_preferences = new Shared_Preferences(getApplicationContext());
        String temp = getIntent().getStringExtra("costumeId");

        if(temp == null){
            Uri uri = getIntent().getData();

            if(uri != null){
                String path = uri.toString();
                String[] parameter = path.split("/");
                costumeId = parameter[5];
            }
        }else{
            costumeId = temp;
        }

        setDay();
        getCostumeInfo();
        setListeners();
    }

    private void loading(boolean value){
        if(value){
            binding.layoutMain1.setVisibility(View.GONE);
            binding.progressBarMain.setVisibility(View.VISIBLE);
        }else{
            binding.layoutMain1.setVisibility(View.VISIBLE);
            binding.progressBarMain.setVisibility(View.GONE);
        }
    }

    private String countDownTime(long milis){
        int seconds = (int) (milis / 1000) % 60;
        int minutes = (int) ((milis / (1000 * 60)) % 60);
        int hours = (int) ((milis / (1000 * 60 * 60)) % 24);
        int days = (int) ((milis / (1000 * 60 * 60)) / 24);

        if (days > 0)
            return days+" ngày";
        else
            return hours + ":" + minutes + ":" + seconds;
    }

    private int day(long milis){
        return (int) ((milis / (1000 * 60 * 60)) / 24);
    }

    private void setDay(){
        java.time.LocalDate dateStart = java.time.LocalDate.now();
        dateStart = dateStart.plusDays(3);
        java.time.LocalDate dateEnd = java.time.LocalDate.now();
        dateEnd = dateEnd.plusDays(5);

        binding.txt5.setText( dateStart.getDayOfMonth()+"/"+dateStart.getMonthValue()+"/"+dateStart.getYear()+" - "+
                dateEnd.getDayOfMonth()+"/"+dateEnd.getMonthValue()+"/"+dateEnd.getYear());
    }

    private void setEventHasNotStart(Costume costume){
        costumeHasEvent(costume);

        binding.txtDiscount.setVisibility(View.GONE);
        binding.txtNoteEvent.setVisibility(View.GONE);
        binding.txtValueEvent.setVisibility(View.GONE);
    }

    private void eventRunning(Costume costume){
        costumeHasEvent(costume);

        int discount = costume.getPrice()*(100-costume.getPromotion().getValue())/100;
        binding.txtPrice.setText(intConvertMoney(discount));
        binding.txtDiscount.setText(Html.fromHtml("<strike>"+intConvertMoney(costume.getPrice())+"</strike>"));
        binding.txtPrice.setTextColor(getResources().getColor(R.color.color1));
        binding.txtNoteEvent.setText("Tiết kiệm: "+intConvertMoney(costume.getPrice()-discount));
        binding.txtValueEvent.setText("-"+costume.getPromotion().getValue()+"%");
    }

    private void costumeHasEvent(Costume costume){
        if(!costume.getPromotion().getIcon().equals("")) {
            Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    binding.imgIcon.setImageResource(R.drawable.ic_baseline_flash_on_24);
                }
            });
            Picasso pic = builder.build();
            pic.load(costume.getPromotion().getIcon()).into(binding.imgIcon);
        }

        binding.txtNameEvent.setText(costume.getPromotion().getName());
    }

    private void eventHasNotStart(Costume costume, long millisFutureStartTime){
        setEventHasNotStart(costume);

        //check range from nowTime to startTime: result > 1 day ==> only show, <= 1 day ==> count down
        if(day(millisFutureStartTime) > 1){
            binding.txtCountDown.setText(getApplication().getString(R.string.start_event) + " " + countDownTime(millisFutureStartTime));
        }else{
            countDownTimer = new CountDownTimer(millisFutureStartTime, 1000) {

                public void onTick(long millisUntilFinished) {
                    binding.txtCountDown.setText(getApplication().getString(R.string.start_event) + " " + countDownTime(millisUntilFinished));
                }

                public void onFinish() {

                }
            }.start();
        }
    }

    private void eventHasStart(Costume costume){
        long millisFutureEndTime = RangeTime.getBetweenDayToNow(costume.getPromotion().getEndTime());

        //check endTime with nowTime: result < 0 => endEvent
        if(millisFutureEndTime >= 0){
            eventRunning(costume);

            //check range from nowTime from endTime: result > 1 day ==> only show, <= 1 day ==> count down
            if(day(millisFutureEndTime) > 1){
                binding.txtCountDown.setText(getApplication().getString(R.string.end_event) + " " + countDownTime(millisFutureEndTime));
            }else{
                countDownTimer = new CountDownTimer(millisFutureEndTime, 1000) {

                    public void onTick(long millisUntilFinished) {
                        binding.txtCountDown.setText(getApplication().getString(R.string.end_event) + " " + countDownTime(millisUntilFinished));
                    }

                    public void onFinish() {
                        binding.txtCountDown.setText(getApplication().getString(R.string.done_event));
                    }
                }.start();
            }
        }else{
            endEvent();
        }
    }

    private void endEvent(){
        binding.layoutEvent.setVisibility(View.GONE);
        binding.txtDiscount.setVisibility(View.GONE);
        binding.txtNoteEvent.setVisibility(View.GONE);
        binding.txtValueEvent.setVisibility(View.GONE);
    }

    private void setPromotion(Costume costume){
        if(costume.getPromotion() != null){
            long millisFutureStartTime = RangeTime.getBetweenDayToNow(costume.getPromotion().getStartTime());

            //check startTime with nowTime: result > 0 => nowTime < startTime => event hasn't start
            if(millisFutureStartTime > 0){
                eventHasNotStart(costume, millisFutureStartTime);
            }else{
                eventHasStart(costume);
            }
        }else{
            endEvent();
        }
    }

    private void sizeGuide(){
        SizeGuideDialog dialog = new SizeGuideDialog(CostumeActivity.this, sizeList, new SizeGuideDialog.SizeGuideListeners() {
            @Override
            public void onClick(String result) {
                int position = 0;
                for(int i = 0; i < sizeList.size(); i ++){
                    if(sizeList.get(i).getName().equals(result)){
                        position = i;
                        break;
                    }
                }

                if(preSize != -1){
                    sizeList.get(preSize).setCheck(false);
                    sizeAdapter.notifyItemChanged(preSize);
                }

                preSize = position;
                sizeList.get(position).setCheck(true);
                binding.txtSize.setText(getApplication().getString(R.string.size)+"  "+result);

                sizeAdapter.notifyItemChanged(position);

                binding.errorSize.setVisibility(View.GONE);
            }
        }, costumeStyleId);

        dialog.show();
        dialog.getWindow().setLayout(700, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void setListeners(){
        binding.layoutMain.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    binding.layoutHeader.setVisibility(View.VISIBLE);
                }

                if (scrollY < 5) {
                    binding.layoutHeader.setVisibility(View.GONE);
                }

                if(scrollY >= 850){
                    binding.txtInfoCostume.setTextColor(getResources().getColor(R.color.secondary_text, null));
                    binding.txtIntroCostume.setTextColor(getResources().getColor(R.color.black, null));
                    binding.layoutIntroCostume.setVisibility(View.VISIBLE);
                    binding.layoutInfoCostume.setVisibility(View.GONE);
                }else{
                    binding.txtInfoCostume.setTextColor(getResources().getColor(R.color.black, null));
                    binding.txtIntroCostume.setTextColor(getResources().getColor(R.color.secondary_text, null));
                    binding.layoutIntroCostume.setVisibility(View.GONE);
                    binding.layoutInfoCostume.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.txtInfoCostume.setOnClickListener(v -> {
            binding.layoutMain.setScrollY(0);
        });

        binding.txtIntroCostume.setOnClickListener(v -> {
            binding.layoutMain.setScrollY(850);
        });

        // get current position of recyclerView
        binding.rclImg.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) binding.rclImg.getLayoutManager();
                        preIndexImg = linearLayoutManager.findFirstVisibleItemPosition();
                        binding.txtPosition.setText((preIndexImg+1)+"/"+pictures.size());
                        break;
                    default:
                        break;
                }
            }
        });

        binding.layoutDes1.setOnClickListener(v -> {
            binding.layoutDescription.setVisibility(View.VISIBLE);
        });

        binding.btnClose.setOnClickListener(v -> {
            binding.layoutDescription.setVisibility(View.GONE);
        });

        binding.layoutD.setOnClickListener(v -> {
            binding.layoutDescription.setVisibility(View.GONE);
        });

        binding.layoutPolicy.setOnClickListener(v -> {
            binding.layoutPolicy1.setVisibility(View.VISIBLE);
        });

        binding.btnClosePolicy.setOnClickListener(v -> {
            binding.layoutPolicy1.setVisibility(View.GONE);
        });

        binding.layoutSize.setOnClickListener(v -> {
            //binding.layoutSize1.setVisibility(View.VISIBLE);
            sizeGuide();
        });

        binding.btnCloseSize.setOnClickListener(v -> {
            binding.layoutSize1.setVisibility(View.GONE);
        });

        binding.imgFavourite.setOnClickListener(v -> {
            setFavourite();
        });

        binding.imgShare.setOnClickListener(v -> {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String shareMessage= costumeName+"\n\n";
                shareMessage = shareMessage + "https://smartfashion.covid21tsp.space/Share/Index/" + costumeId;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }
        });

        binding.btnAddCart.setOnClickListener(v -> {
            if(!isAdd)
                addCart();
            else{
                Intent intent = new Intent(getApplication(), CartActivity.class);
                startActivity(intent);
            }
        });

        binding.imgFavouriteTotal.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), FavouriteActivity.class);
            startActivity(intent);
        });

        binding.imgCart.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), CartActivity.class);
            startActivity(intent);
        });

        binding.btnBackBack.setOnClickListener(v -> {
            finish();
        });

        binding.imgEye.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CoordinatesActivity.class);
            intent.putExtra("costume", costume);
            startActivity(intent);
        });
    }

    public void setFavourite(){
        Service service = getRetrofit().create(Service.class);
        Call<Message> favourite = service.AddFavourite("bearer "+shared_preferences.getToken(), costumeId);
        favourite.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.body().getStatus() == 1){
                    isFavorite = !isFavorite;
                    int quantity = Integer.parseInt(binding.txtNumberFavourite.getText()+"");

                    if(isFavorite) {
                        binding.imgFavourite.setImageResource(R.drawable.ic_baseline_favorite_24);

                        quantity ++;
                    }
                    else {
                        binding.imgFavourite.setImageResource(R.drawable.ic_favourite_none);

                        quantity --;
                    }

                    binding.txtNumberFavourite.setText(quantity +"");
                    shared_preferences.saveQuantityCart(quantity);
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

            }
        });
    }

    private boolean validateAddCate(){
        boolean is = true;

        if(preColor == -1 && sizeList != null){
            is = false;
            binding.errorColor.setVisibility(View.VISIBLE);
        }

        if(preSize == -1 && colorList != null){
            is = false;
            binding.errorSize.setVisibility(View.VISIBLE);
        }

        return is;
    }

    public void addCart(){
        if(validateAddCate()) {
            ColorObject color = new ColorObject();
            if(preColor != -1)
                color = colorList.get(preColor);

            String size = "";
            if(preSize != -1)
                size = sizeList.get(preSize).getName();

            Service service = getRetrofit().create(Service.class);
            Call<Message> cart = service.AddCart("bearer " + shared_preferences.getToken(), new Request_AddCart(
                    costumeId,
                    color,
                    size
            ));
            cart.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    if (response.body().getStatus() == 1) {
                        //0 có nghĩa là sản phẩm đã sẳn trong giỏ hàng
                        if (response.body().getId().equals("0")) {
                            int quantity = Integer.parseInt(binding.txtNumber.getText() + "");

                            quantity++;
                            binding.txtNumber.setText(quantity + "");
                            shared_preferences.saveQuantityCart(quantity);
                        }

                        binding.imgCostume.setVisibility(View.VISIBLE);
                        AnimatorSet animSetXY = new AnimatorSet();
                        if(isAdd) {
                            binding.imgCostume.setY(binding.imgCostumeOld.getY());
                            binding.imgCostume.setX(binding.imgCostumeOld.getX());
                        }else{
                            isAdd = true;
                        }
                        ObjectAnimator y = ObjectAnimator.ofFloat(binding.imgCostume, "translationY", binding.imgCart.getY()-binding.imgCostumeOld.getY());
                        ObjectAnimator x = ObjectAnimator.ofFloat(binding.imgCostume, "translationX", binding.imgCostumeOld.getLeft(), binding.imgCart.getLeft());
                        ObjectAnimator sy = ObjectAnimator.ofFloat(binding.imgCostume, "scaleY", 2f, 0.1f);
                        ObjectAnimator sx = ObjectAnimator.ofFloat(binding.imgCostume, "scaleX", 2f, 0.1f);
                        animSetXY.playTogether(x, y, sx, sy);
                        animSetXY.setDuration(1500);
                        animSetXY.start();

                        binding.btnAddCart.setText("Xem giỏ hàng");
                    }
                    else
                        Toast.makeText(getApplication(), response.body().getNotification(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {
                    Toast.makeText(getApplication(), "Thêm dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setImg(List<Picture> data){
        costumeImgAdapter = new CostumeImgAdapter(data, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

        binding.rclImg.setLayoutManager(gridLayoutManager);
        binding.rclImg.setAdapter(costumeImgAdapter);
    }

    private void setSuitableOutfit(){
        suitableOutfit = new CostumeAdapter(suitableOutfitCostumes, this, new CostumeAdapter.CostumeListeners() {
            @Override
            public void onClickFavourite(CostumeHome costume, int position) {
                binding.txtNumberFavourite.setText(shared_preferences.getQuantityFavorite());

                for(int i = 0; i < relatedCostumes.size(); i++){
                    if(relatedCostumes.get(i).getCostume().getId().equals(costume.getCostume().getId())){
                        relatedCostumes.get(i).setFavourite(costume.getFavourite());

                        costumeAdapter.notifyItemChanged(i);
                        i = suitableOutfitCostumes.size()+1;
                    }
                }
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclSuitableOutfit.setLayoutManager(gridLayoutManager);
        binding.rclSuitableOutfit.setAdapter(suitableOutfit);
    }

    private void setRelatedCostumes(){
        costumeAdapter = new CostumeAdapter(relatedCostumes, this, new CostumeAdapter.CostumeListeners() {
            @Override
            public void onClickFavourite(CostumeHome costume, int position) {
                binding.txtNumberFavourite.setText(shared_preferences.getQuantityFavorite());

                for(int i = 0; i < suitableOutfitCostumes.size(); i++){
                    if(suitableOutfitCostumes.get(i).getCostume().getId().equals(costume.getCostume().getId())){
                        suitableOutfitCostumes.get(i).setFavourite(costume.getFavourite());

                        suitableOutfit.notifyItemChanged(i);
                        i = suitableOutfitCostumes.size()+1;
                    }
                }
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclRelatedCostumes.setLayoutManager(gridLayoutManager);
        binding.rclRelatedCostumes.setAdapter(costumeAdapter);
    }

    private void setStyle(){
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < 20; i ++){
            temp.add("Sy");
        }

        styleAdapter = new StyleAdapter(personalStyles, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

        binding.rclStyles.setLayoutManager(gridLayoutManager);
        binding.rclStyles.setAdapter(styleAdapter);
    }

    private void setBody(){
        bodyAdapter = new BodyCostumeAdapter(bodyList, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

        binding.rclBodys.setLayoutManager(gridLayoutManager);
        binding.rclBodys.setAdapter(bodyAdapter);
    }

    private void setSize(){
        sizeAdapter = new SizeAdapter(sizeList, this, new SizeAdapter.SizeListeners() {
            @Override
            public void onClick(Size size, int position) {
                if(size.isCheck()) {
                    if(preSize != -1){
                        sizeList.get(preSize).setCheck(false);
                        sizeAdapter.notifyItemChanged(preSize);
                    }

                    preSize = position;
                    sizeList.get(position).setCheck(size.isCheck());
                    binding.txtSize.setText(getApplication().getString(R.string.size)+"  "+size.getName());

                    binding.errorSize.setVisibility(View.GONE);
                }else{
                    preSize = -1;
                    binding.txtSize.setText(getApplication().getString(R.string.size));
                }
            }
        }, 1);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

        binding.rclSize.setLayoutManager(gridLayoutManager);
        binding.rclSize.setAdapter(sizeAdapter);
    }

    private void setColor(){
        colorAdapter = new ColorAdapter(colorList, this, new ColorAdapter.ColorListeners() {
            @Override
            public void onClick(int position, ColorObject colorObject) {
                if(colorObject.isCheck()) {
                    if(preColor != -1){
                        colorList.get(preColor).setCheck(false);
                        colorAdapter.notifyItemChanged(preColor);
                    }

                    preColor = position;
                    colorList.get(position).setCheck(colorObject.isCheck());
                    binding.txtColor.setText(getApplication().getString(R.string.color)+" "+colorObject.getName());

                    binding.errorColor.setVisibility(View.GONE);

                    newPictures = new ArrayList<>();
                    for (int i = 0; i < pictures.size(); i++) {
                        if(pictures.get(i).getCodeColor().equals(colorObject.getCode())){
                            newPictures.add(pictures.get(i));
                        }
                    }

                    if(newPictures.size() != pictures.size()) {
                        setImg(newPictures);
                        binding.txtPosition.setText((preIndexImg + 1) + "/" + newPictures.size());
                    }
                }else{
                    preColor = -1;
                    binding.txtColor.setText(getApplication().getString(R.string.color));
                    setImg(pictures);
                    binding.txtPosition.setText((preIndexImg+1)+"/"+pictures.size());
                }
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

        binding.rclColor.setLayoutManager(gridLayoutManager);
        binding.rclColor.setAdapter(colorAdapter);
    }

    private void getCostumeInfo(){
        Log.i("error", costumeId);
        loading(true);

        String token = shared_preferences.getToken();
        if(getBetweenDayToCurrent(shared_preferences.getTime()) > 0){
            token = token_client;
        }

        Service service = getRetrofit().create(Service.class);
        Call<Message_Costume> costumeCall = service.GetCostume("bearer "+token, costumeId);
        costumeCall.enqueue(new Callback<Message_Costume>() {
            @Override
            public void onResponse(Call<Message_Costume> call, Response<Message_Costume> response) {
                if(response.body().getStatus() == 1){
                    pictures = response.body().getCostume().getCostume().getPictures();
                    personalStyles = response.body().getCostume().getPersonalStyles();
                    bodyList = response.body().getCostume().getBodies();
                    relatedCostumes = response.body().getCostume().getRelatedCostumes();
                    suitableOutfitCostumes = response.body().getCostume().getSuitableOutfitCostumes();
                    isFavorite = response.body().getCostume().getFavourite();
                    costumeName = response.body().getCostume().getCostume().getName();
                    colorList = response.body().getCostume().getCostume().getColors();
                    sizeList = response.body().getCostume().getCostume().getSizes();
                    costumeStyleId = response.body().getCostume().getCostume().getCostumeStyleId();
                    costume = response.body().getCostume().getCostume();

                    setImg(pictures);
                    setStyle();
                    setBody();
                    setRelatedCostumes();
                    setSuitableOutfit();

                    if(sizeList != null)
                    {
                        for(int i = 0; i < sizeList.size(); i ++){
                            if(sizeList.get(i).isCheck()){
                                preSize = i;
                                binding.txtSize.setText(getApplication().getString(R.string.size)+"  "+sizeList.get(i).getName());
                                break;
                            }
                        }
                        setSize();
                    }else{
                        binding.layoutSizeCostume.setVisibility(View.GONE);
                    }
                    if(colorList != null)
                        setColor();
                    else
                        binding.layoutColor.setVisibility(View.GONE);

                    Picasso.Builder builder = new Picasso.Builder(getApplication());
                    builder.listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            binding.imgMain.setImageResource(R.drawable.logo);
                            binding.imgCostume.setImageResource(R.drawable.logo);
                        }
                    });
                    Picasso pic = builder.build();
                    pic.load(pictures.get(0).getLink()).into(binding.imgMain);
                    pic.load(pictures.get(0).getLink()).into(binding.imgCostume);

                    binding.txtName.setText(costumeName);
                    binding.txtPrice.setText(intConvertMoney(response.body().getCostume().getCostume().getPrice()));
                    binding.txtNumber.setText(response.body().getCostume().getQuantityCart()+"");
                    binding.txtNumberFavourite.setText(response.body().getCostume().getQuantityFavourite()+"");
                    binding.txtPosition.setText("1/"+pictures.size());

                    setPromotion(response.body().getCostume().getCostume());

                    String descriptionCostume = response.body().getCostume().getCostume().getDescription();
                    String description = " ";
                    String descriptionList[] = descriptionCostume.split(",");
                    for (int i = 0; i< descriptionList.length; i++){
                        description += descriptionList[i] +"\n";
                    }
                    binding.txtDescription.setText(description);

                    if(isFavorite)
                        binding.imgFavourite.setImageResource(R.drawable.ic_baseline_favorite_24);
                    else
                        binding.imgFavourite.setImageResource(R.drawable.ic_favourite_none);
                }else{
                    Toast.makeText(getApplication(), response.body().getNotification(), Toast.LENGTH_SHORT).show();
                    Log.i("error", response.body().getNotification());
                    finish();
                }
                loading(false);
            }

            @Override
            public void onFailure(Call<Message_Costume> call, Throwable t) {
                Toast.makeText(getApplication(), "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                loading(false);
                finish();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if(countDownTimer != null)
            countDownTimer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(countDownTimer != null)
            countDownTimer.cancel();
    }
}